// FrontEnd/App.js
// Script to handle all user-related operations

// Fetch all employees and display them in the table
function fetchAllEmployees() {
  fetch('http://localhost:8080/newhireinfo')
    .then(response => response.json())
    .then(employees => {
      const employeeTableBody = document.getElementById('employeeData');
      employeeTableBody.innerHTML = ''; // Clear existing rows
      employees.forEach(employee => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${employee.employeeId}</td>
          <td>${employee.name}</td>
          <td>${employee.isMentor ? 'Mentor' : 'Mentee'}</td>
          <td>${employee.employmentType}</td>
          <td>
            <button class="btn btn-success" onclick="viewEmployee(${employee.employeeId})"><i class="bi bi-eye"></i></button>
            <button class="btn btn-primary" onclick="editEmployee(${employee.employeeId})"><i class="bi bi-pencil-square"></i></button>
            <button class="btn btn-danger" onclick="deleteEmployee(${employee.employeeId})"><i class="bi bi-trash"></i></button>
          </td>
        `;
        employeeTableBody.appendChild(row);
      });
    })
    .catch(error => {
      console.error('Error fetching employees:', error);
    });
}

// Function to create a new employee
function createEmployee(employeeData) {
  fetch('http://localhost:8080/newhireinfo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(employeeData)
  })
  .then(response => {
    if (response.ok) {
      fetchAllEmployees(); // Refresh the employee list
      document.getElementById('newEmployeeForm').reset(); // Reset the form
      $('#newEmployeeModal').modal('hide'); // Close the modal
    } else {
      // Handle errors
      alert('Failed to create employee.');
    }
  });
}

// Function to handle the form submission for adding a new employee
document.getElementById('newEmployeeForm').addEventListener('submit', function(event) {
  event.preventDefault();
  const newEmployeeData = {
    name: document.getElementById('newName').value,
    isMentor: document.getElementById('newIsMentor').checked,
    employmentType: document.getElementById('newEmploymentType').value
  };
  createEmployee(newEmployeeData);
});

// Function to update an existing employee
function updateEmployee(employeeId, employeeData) {
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(employeeData)
  })
  .then(response => {
    if (response.ok) {
      fetchAllEmployees(); // Refresh the employee list
      $('#editEmployeeModal').modal('hide'); // Close the modal
    } else {
      // Handle errors
      alert('Failed to update employee.');
    }
  });
}

// Function to view an employee's details
function viewEmployee(employeeId) {
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
    .then(response => response.json())
    .then(employee => {
      // Display the employee's details
      // For example, you can show a modal with the employee's information
      alert(`Employee Details:\nName: ${employee.name}\nIs Mentor: ${employee.isMentor ? 'Yes' : 'No'}\nEmployment Type: ${employee.employmentType}`);
    });
}

// Function to delete an employee
function deleteEmployee(employeeId) {
  if (confirm('Are you sure you want to delete this employee?')) {
    fetch(`http://localhost:8080/newhireinfo/${employeeId}`, {
      method: 'DELETE'
    })
    .then(response => {
      if (response.ok) {
        fetchAllEmployees(); // Refresh the employee list
      } else {
        // Handle errors
        alert('Failed to delete employee.');
      }
    });
  }
}

// Function to populate mentor assignments for an employee
function populateMentorAssignments(employeeId) {
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
    .then(response => response.json())
    .then(employee => {
      const mentorAssignmentsSelect = document.getElementById('editMentorAssignments');
      mentorAssignmentsSelect.innerHTML = ''; // Clear existing options

      if (employee.isMentor) {
        // If the employee is a mentor, fetch and list their mentees
        fetch(`http://localhost:8080/newhireinfo/${employeeId}/mentees`)
          .then(response => response.json())
          .then(mentees => {
            mentees.forEach(mentee => {
              const option = document.createElement('option');
              option.value = mentee.employeeId;
              option.textContent = `${mentee.name} (Mentee)`;
              mentorAssignmentsSelect.appendChild(option);
            });
          });
      } else {
        // If the employee is a mentee, note their mentor
        fetch(`http://localhost:8080/mentorassignments/mentee/${employeeId}`)
          .then(response => response.json())
          .then(assignment => {
            if (assignment) {
              const option = document.createElement('option');
              option.value = assignment.mentor.employeeId;
              option.textContent = `${assignment.mentor.name} (Mentor)`;
              option.selected = true;
              mentorAssignmentsSelect.appendChild(option);
            } else {
              // If the mentee is unassigned, provide a list of mentors to choose from
              fetch(`http://localhost:8080/newhireinfo/mentors`)
                .then(response => response.json())
                .then(mentors => {
                  mentors.forEach(mentor => {
                    const option = document.createElement('option');
                    option.value = mentor.employeeId;
                    option.textContent = `${mentor.name} (Mentor)`;
                    mentorAssignmentsSelect.appendChild(option);
                  });
                });
            }
          });
      }
    });
}

// Function to edit an employee's details
function editEmployee(employeeId) {
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
    .then(response => response.json())
    .then(employee => {
      // Populate the edit form with the employee's details
      document.getElementById('editEmployeeId').value = employee.employeeId;
      document.getElementById('editName').value = employee.name;
      document.getElementById('editIsMentor').checked = employee.isMentor;
      document.getElementById('editEmploymentType').value = employee.employmentType;

      // Populate mentor assignments
      populateMentorAssignments(employeeId);

            // Show the edit modal
            new bootstrap.Modal(document.getElementById('editEmployeeModal')).show();
        })
        .catch(error => console.error('Error fetching employee details:', error));
}

// Function to handle the form submission for updating an employee
document.getElementById('editEmployeeForm').addEventListener('submit', function(event) {
  event.preventDefault();
  const employeeId = document.getElementById('editEmployeeId').value;
  const updatedEmployeeData = {
    name: document.getElementById('editName').value,
    isMentor: document.getElementById('editIsMentor').checked,
    employmentType: document.getElementById('editEmploymentType').value
  };
  updateEmployee(employeeId, updatedEmployeeData);
});

// Call fetchAllEmployees on page load to populate the table
document.addEventListener('DOMContentLoaded', function () {
  fetchAllEmployees();

  var newEmployeeForm = document.getElementById('newEmployeeForm');
  if (newEmployeeForm) {
      newEmployeeForm.addEventListener('submit', function (event) {
          event.preventDefault();
          // Your code to create a new employee...
      });
  }
});

