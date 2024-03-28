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

// Function that Populates the employee ID, name, mentor status, and employment type in the edit form.
// It's a basic version that covers essential employee attributes but lacks fields like user type, username, password, and email.
function editEmployeeBasic(employeeId) {
    fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
      .then(response => response.json())
      .then(employee => {
        // Populate the edit form with the employee data
        document.getElementById('editEmployeeId').value = employee.employeeId;
        document.getElementById('editName').value = employee.name;
        document.getElementById('editIsMentor').checked = employee.isMentor;
        document.getElementById('editEmploymentType').value = employee.employmentType;
        // Show the edit modal
        $('#editEmployeeModal').modal('show');
      });
}

// Function that ncludes more fields: name, user type, username, password, email, and employment type.
// Provides a comprehensive editing experience by covering all aspects of an employee's record.
// It's more thorough than the basic version and suitable for a detailed employee management system.
function editEmployee(employeeId) {
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
      .then(response => response.json())
      .then(employee => {
          // Populate the edit form with the employee's details
          document.getElementById('editEmployeeId').value = employee.employeeId;
          document.getElementById('editName').value = employee.name;
          document.getElementById('editUserType').value = employee.userType;
          document.getElementById('editUsername').value = employee.username;
          document.getElementById('editPassword').value = employee.password; // Consider security implications
          document.getElementById('editEmail').value = employee.email;
          document.getElementById('editEmploymentType').value = employee.employmentType;
          // Add logic to populate Mentor Assignments and Tasks
          
          // Show the edit modal
          $('#editEmployeeModal').modal('show');
      });
}

// Function that Assumes the existence of a function loadEmployeeDetails that handles the population of the edit form.
// It's a simplified version that separates the concerns of fetching employee data and displaying the modal.
// The thoroughness of this version depends on the implementation of loadEmployeeDetails.
function editEmployeeSimple(employeeId) {
  loadEmployeeDetails(employeeId);
  $('#editEmployeeModal').modal('show');
}

// Function Similar to the editEmployee version but named differently.
// It's as thorough as the editEmployee version, covering all necessary fields for a comprehensive editing experience. 
function editEmployeeCombined(employeeId) {
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
      .then(response => response.json())
      .then(employee => {
          // Populate the edit form with the employee's details
          document.getElementById('editEmployeeId').value = employee.employeeId;
          document.getElementById('editName').value = employee.name;
          document.getElementById('editUserType').value = employee.userType;
          document.getElementById('editUsername').value = employee.username;
          document.getElementById('editPassword').value = employee.password; // Consider security implications
          document.getElementById('editEmail').value = employee.email;
          document.getElementById('editEmploymentType').value = employee.employmentType;
          // Add logic to populate Mentor Assignments and Tasks
          
          // Show the edit modal
          $('#editEmployeeModal').modal('show');
      });
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

document.addEventListener("DOMContentLoaded", function () {
  // Fetch and display all employees when the page loads
  fetchAllEmployees();

  // Event listener for the form submission
  document.getElementById('employeeForm').addEventListener('submit', function(event) {
      event.preventDefault();
      const employeeData = {
          name: document.getElementById('employeeName').value,
          email: document.getElementById('employeeEmail').value,
          userType: document.getElementById('employeeType').value,
          username: document.getElementById('employeeUsername').value,
          password: document.getElementById('employeePassword').value, // Consider security implications
          employmentType: document.getElementById('employeeEmploymentType').value,
          // Add logic to handle mentor assignments and tasks
      };
      // Add logic to determine whether to create or update an employee
      // For example, you could check if there's an existing employee ID
  });
});

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

// Call fetchAllEmployees on page load to populate the table
document.addEventListener("DOMContentLoaded", function () {
  fetchAllEmployees();
});

// Fetch all unassigned mentees and highlight them in the table
function fetchUnassignedMentees() {
  fetch('http://localhost:8080/newhireinfo/unassigned-mentees')
      .then(response => response.json())
      .then(unassignedMentees => {
          const employeeTableBody = document.getElementById('employeeData');
          const rows = employeeTableBody.getElementsByTagName('tr');
          for (let row of rows) {
              const employeeName = row.cells[1].textContent;
              if (unassignedMentees.includes(employeeName)) {
                  row.classList.add('table-warning'); // Highlight the row
              }
          }
      });
}

// Fetch all mentors and populate a select dropdown
function fetchAllMentors() {
  fetch('http://localhost:8080/newhireinfo/mentors')
      .then(response => response.json())
      .then(mentors => {
          const mentorSelect = document.getElementById('mentorSelect');
          mentors.forEach(mentor => {
              const option = document.createElement('option');
              option.value = mentor.employeeId;
              option.textContent = mentor.name;
              mentorSelect.appendChild(option);
          });
      });
}

// Create a mentor assignment
function createMentorAssignment(mentorId, menteeId) {
  const assignmentData = {
      mentorId: mentorId,
      menteeId: menteeId
  };
  fetch('http://localhost:8080/mentorassignments', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(assignmentData)
  })
      .then(response => {
          if (response.ok) {
              fetchAllEmployees(); // Refresh the employee list
              fetchUnassignedMentees(); // Refresh the unassigned mentees
          } else {
              // Handle errors
              alert('Failed to create mentor assignment.');
          }
      });
}

// Call these functions on page load
document.addEventListener("DOMContentLoaded", function () {
  fetchAllEmployees();
  fetchUnassignedMentees();
  fetchAllMentors();
});


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



function loadEmployeeDetails(employeeId) {
  // Fetch employee details by ID
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
    .then(response => response.json())
    .then(employee => {
      // Set form fields with employee details
      document.getElementById('editEmployeeId').value = employee.employeeId;
      document.getElementById('editName').value = employee.name;
      // Other form fields...

      // Check if the employee is a mentor or mentee
      if (employee.userType === 'Mentor') {
        // Fetch and display mentees assigned to this mentor
        fetchMenteesByMentor(employee.employeeId);
      } else if (employee.userType === 'Mentee') {
        // Check if the mentee is unassigned and display mentors for selection
        checkUnassignedMentee(employee.employeeId);
      }
    });
}

function fetchMenteesByMentor(mentorId) {
  fetch(`http://localhost:8080/newhireinfo/${mentorId}/mentees`)
    .then(response => response.json())
    .then(mentees => {
      const mentorAssignmentsSelect = document.getElementById('editMentorAssignments');
      mentorAssignmentsSelect.innerHTML = ''; // Clear existing options
      mentees.forEach(mentee => {
        const option = document.createElement('option');
        option.value = mentee.employeeId;
        option.textContent = `${mentee.name} (Mentee)`;
        mentorAssignmentsSelect.appendChild(option);
      });
    });
}

function checkUnassignedMentee(menteeId) {
  // This function would need to check if the mentee is unassigned
  // and then fetch and display a list of mentors for selection
  // This could involve additional endpoints and logic
}



