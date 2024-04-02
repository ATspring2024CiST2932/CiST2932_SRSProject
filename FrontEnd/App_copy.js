// FrontEnd/App.jsjsx
// Script to handle all user-related operations
import 'bootstrap/dist/css/bootstrap.min.css';
// import 'bootstrap-icons/font/bootstrap-icons.css';

document.addEventListener('DOMContentLoaded', function () {
  // Fetch all employees and populate the table
  fetchAllEmployees();

  // Event listener for the 'New Employee' form submission
  document.getElementById('newEmployeeForm').addEventListener('submit', function(event) {
      event.preventDefault();
      const formData = {
          name: document.getElementById('newEmployeeName').value,
          email: document.getElementById('newEmployeeEmail').value,
          isMentor: document.getElementById('newEmployeeIsMentor').checked,
          username: document.getElementById('newEmployeeUsername').value,
          password: document.getElementById('newEmployeePassword').value,
          employmentType: document.getElementById('newEmployeeEmploymentType').value
      };
      createEmployee(formData);
  });

  // Event listener for the 'Edit Employee' form submission
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

  // Event listener for the 'Add Task' button
  document.getElementById('addTaskButton').addEventListener('click', function() {
      $('#addTaskModal').modal('show');
  });

  // Event listener for the 'Add Task' form submission
  document.getElementById('addTaskForm').addEventListener('submit', function(event) {
      event.preventDefault();
      const taskData = {
          description: document.getElementById('taskDescription').value,
          type: document.getElementById('taskType').value,
          // Include other task details as needed
      };
      // Send taskData to your backend to add the new task
      // For example, using fetch() if you're using a REST API
      fetch('http://localhost:8080/peercodingtasks', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(taskData)
      })
      .then(response => {
          if (response.ok) {
              // Handle success, such as refreshing the task list or closing the modal
              $('#addTaskModal').modal('hide');
          } else {
              // Handle errors
              alert('Failed to add task.');
          }
      });
  });

  // Event delegation for employee actions
  document.getElementById('employeeTableBody').addEventListener('click', function(event) {
    if (event.target.classList.contains('view-btn')) {
        const employeeId = event.target.dataset.employeeId;
        viewEmployee(employeeId);
    } else if (event.target.classList.contains('edit-btn')) {
        const employeeId = event.target.dataset.employeeId;
        editEmployee(employeeId);
    } else if (event.target.classList.contains('archive-btn')) {
        const employeeId = event.target.dataset.employeeId;
        archiveEmployee(employeeId);
    }
});


  // Call toggleMentorAssignmentSection when the checkbox changes
  document.getElementById('newEmployeeIsMentor').addEventListener('change', toggleMentorAssignmentSection);
  });

// Function to populate mentor assignments for an employee
function populateMentorAssignments(employeeId) {
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
    .then(response => response.json())
    .then(employee => {
      const mentorAssignmentsSelect = document.getElementById('viewMentorAssignments');
      mentorAssignmentsSelect.innerHTML = ''; // Clear existing options

      if (employee.isMentor) {
        // If the employee is a mentor, fetch and list their mentees
        fetch(`http://localhost:8080/newhireinfo/${employeeId}/mentees`)
          .then(response => response.json())
          .then(mentees => {
            console.log('Mentees:', mentees)
            employee.assignmentsAsMentor.forEach(assignment => {
              const li = document.createElement('li');
              li.textContent = `Assignment ID: ${assignment.assignmentId}, Mentee ID: ${assignment.menteeId}`;
              viewMentorAssignments.appendChild(li);
            });            
          });
      } else {
        // If the employee is a mentee, note their mentor
        fetch(`http://localhost:8080/newhireinfo/${employeeId}/mentees`)
          .then(response => response.json())
          .then(assignment => {
            console.log('Mentor assignment:', assignment)
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
                  console.log('Select Mentor:', mentors)
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

// Function to populate tasks for an employee
function populateTasks(employeeId) {
  fetch(`http://localhost:8080/peercodingtasks/mentor/${employeeId}/tasks`)
      .then(response => response.json())
      .then(tasks => {
        console.log('Tasks:', tasks);
        const tasksList = document.getElementById('viewTasks');
        tasksList.innerHTML = ''; // Clear existing list items
  
        tasks.forEach(task => {
          const li = document.createElement('li');
          li.textContent = `Task ID: ${task.taskId}, Task Type: ${task.taskType}, Total Hours: ${task.totalHours}`;
          tasksList.appendChild(li);
        });
      });
}

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
          <td>${employee.employmentType}</td>
          <td>${employee.isMentor ? 'Yes' : 'No'}</td>
          <td>
            <button class="btn btn-success" onclick="viewEmployee(${employee.employeeId})"><i class="bi bi-eye"></i></button>
            <button class="btn btn-primary" onclick="editEmployee(${employee.employeeId})"><i class="bi bi-pencil-square"></i></button>
            <button class="btn btn-danger" onclick="archiveEmployee(${employee.employeeId})"><i class="bi bi-trash"></i></button>
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

// // Function to toggle mentor assignment section based on the 'isMentor' checkbox
// function toggleMentorAssignmentSection() {
//     const isMentorCheckbox = document.getElementById('newEmployeeIsMentor');
//     const mentorAssignmentSection = document.getElementById('mentorAssignmentSection');
//     const mentorAssignmentsSelect = document.getElementById('newMentorAssignments');

//     // Show or hide the mentor assignment section
//     mentorAssignmentSection.style.display = isMentorCheckbox.checked ? 'block' : 'none';

//     // Clear previous options
//     mentorAssignmentsSelect.innerHTML = '';

//     if (isMentorCheckbox.checked) {
//         // If the employee is a mentor, fetch and populate with mentees (this example assumes you're a mentor looking for mentees)
//         fetchMentees();
//     } else {
//         // If not a mentor, fetch and populate with mentors
//         fetchMentors();
//     }
// }
function toggleMentorAssignmentSection() {
  const isMentorCheckbox = document.getElementById('newEmployeeIsMentor');
  const mentorAssignmentSection = document.getElementById('mentorAssignmentSection');

  // Show or hide the mentor assignment section based on the checkbox state
  mentorAssignmentSection.style.display = isMentorCheckbox.checked ? 'block' : 'none';
}


// Event listener for when the 'New Employee' modal is shown
$('#newEmployeeModal').on('show.bs.modal', function () {
  // Clear the mentor dropdown
  const mentorSelect = document.getElementById('newMentorAssignments');
  mentorSelect.innerHTML = '<option value="">Select Mentor</option>';

  // Fetch and populate mentors
  fetchMentors();
});

// Function to fetch mentors and populate the dropdown
function fetchMentors() {
  fetch('http://localhost:8080/newhireinfo/mentors')
    .then(response => response.json())
    .then(mentors => {
      const mentorSelect = document.getElementById('newMentorAssignments');
      mentorSelect.innerHTML = '<option value="">Select Mentor</option>'; // Clear existing options

      // Filter out duplicate mentors based on employeeId
      const uniqueMentors = mentors.filter((mentor, index, self) =>
        index === self.findIndex((m) => (
          m.employeeId === mentor.employeeId
        ))
      );

      // Populate the dropdown with unique mentors
      uniqueMentors.forEach(mentor => {
        const option = document.createElement('option');
        option.value = mentor.employeeId;
        option.textContent = mentor.name;
        mentorSelect.appendChild(option);
      });
    })
    .catch(error => console.error('Error fetching mentors:', error));
}

// function to fetch mentees and populate the dropdown
function fetchMentees() {
    fetch('http://localhost:8080/newhireinfo/unassigned-mentees')
        .then(response => response.json())
        .then(data => {
            const select = document.getElementById('newMentorAssignments');
            data.forEach(mentee => {
                let option = new Option(mentee.name, mentee.id);
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching mentees:', error));
}

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

};

// Function to edit an employee's details
 function editEmployee(employeeId) {
  console.log(`Editing employee with ID: ${employeeId}`);
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
    .then(response => response.json())
    .then(employee => {
      // Populate the edit form with the employee's details
      document.getElementById('editEmployeeId').value = employee.employeeId;
      document.getElementById('editName').value = employee.name;
      document.getElementById('editIsMentor').checked = employee.isMentor;
      document.getElementById('editEmploymentType').value = employee.employmentType;

      // Populate mentor assignments
      populateMentorAssignments(employeeId); // Populate mentor assignments
      populateTasks(employeeId); // Populate tasks
      // Show the edit modal
      $('#editEmployeeModal').modal('show');

            // Show the edit modal
            // new bootstrap.Modal(document.getElementById('editEmployeeModal')).show();
        })
        // .catch(error => console.error('Error fetching employee details:', error));
}
window.editEmployee = editEmployee;

function viewEmployee(employeeId) {
  console.log(`Viewing employee with ID: ${employeeId}`);
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
      .then(response => response.json())
      .then(employee => {
          // Populate the view modal with the employee's details
          document.getElementById('viewEmployeeId').textContent = employee.employeeId;
          document.getElementById('viewName').textContent = employee.name;
          document.getElementById('viewIsMentor').textContent = employee.isMentor;
          document.getElementById('viewEmploymentType').textContent = employee.employmentType;

          // Populate mentor assignments and tasks for viewing
          populateMentorAssignments(employeeId);
          populateTasks(employeeId);

          // Show the view modal
          $('#viewEmployeeModal').modal('show');
      })
      .catch(error => console.error('Error fetching employee details:', error));
}
window.viewEmployee = viewEmployee;

  // Function to archive an employee
  function archiveEmployee(employeeId) {
    const index = employees.findIndex(e => e.id === employeeId);
    if (index !== -1) {
      employees.splice(index, 1);
      renderEmployees();
    }
  }
  window.archiveEmployee = archiveEmployee;


// Function to delete an employee
function deleteEmployee(employeeId) {
  console.log(`Deleting employee with ID: ${employeeId}`);
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




function handleNewEmployeeSubmit(event) {
  event.preventDefault();
  // Collect form data and call createEmployee
  const formData = {
      name: document.getElementById('newEmployeeName').value,
      email: document.getElementById('newEmployeeEmail').value,
      isMentor: document.getElementById('newEmployeeIsMentor').checked,
      username: document.getElementById('newEmployeeUsername').value,
      password: document.getElementById('newEmployeePassword').value,
      employmentType: document.getElementById('newEmployeeEmploymentType').value
  };
  createEmployee(formData);
}

function handleEditEmployeeSubmit(event) {
  event.preventDefault();
  // Collect form data, including employeeId, and call updateEmployee
  const employeeId = document.getElementById('editEmployeeId').value;
  const updatedEmployeeData = {
      name: document.getElementById('editName').value,
      isMentor: document.getElementById('editIsMentor').checked,
      employmentType: document.getElementById('editEmploymentType').value
  };
  updateEmployee(employeeId, updatedEmployeeData);
}
