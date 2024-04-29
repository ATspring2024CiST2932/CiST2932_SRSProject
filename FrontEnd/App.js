import 'bootstrap/dist/css/bootstrap.min.css';

// Document ready function
document.addEventListener('DOMContentLoaded', function () {
    console.log("Initializing application...");
    console.log("Checking if element exists:", document.getElementById('editEmployeeName'));
 
    // Set default form values
    initializeFormDefaults();

    // Attach all event listeners
    attachEventListeners();

    // Fetch and display all employees on load
    fetchAllEmployees();
});

// Function Checklist and Outline

// initializeFormDefaults()
// Sets default values for form inputs upon page load.
// Example fields to set: newEmployeeName, newEmployeeEmail, etc.
function initializeFormDefaults() {
  console.log("Setting default form values...");
  document.getElementById('newEmployeeName').value = 'John Doe';
  document.getElementById('newEmployeeEmail').value = 'johndoe@example.com';
  document.getElementById('newEmployeeIsMentor').checked = false;
  document.getElementById('newEmployeeUsername').value = 'john.doe';
  document.getElementById('newEmployeePassword').value = 'securepassword123';
  document.getElementById('newEmployeeEmploymentType').value = 'Part-time';
}

// attachEventListeners()
// Attaches all necessary event listeners when the document is ready.
// Includes listeners for form submissions and interactive elements like buttons.
function attachEventListeners() {
  console.log("Attaching event listeners...");
  document.getElementById('newEmployeeIsMentor').addEventListener('change', handleMentorChange);
  document.getElementById('newEmployeeForm').addEventListener('submit', handleSubmitEmployee);
  document.getElementById('editEmployeeForm').addEventListener('submit', handleSubmitEmployee);
  document.getElementById('employeeTable').addEventListener('click', handleTableClick);
  
  // Setup listeners for delete employee modal.
  const deleteModal = document.getElementById('deleteEmployeeModal');
  deleteModal.addEventListener('show.bs.modal', function(event) {
      const button = event.relatedTarget; // Button that triggered the modal
      const employeeId = button.getAttribute('data-employee-id'); // Correctly get employeeId from the button
      console.log("Preparing to delete employee ID:", employeeId);
      document.getElementById('confirmDelete').onclick = function() {
          deleteEmployee(event); // Pass the event to deleteEmployee function
      };
  });
  deleteModal.addEventListener('hidden.bs.modal', function() {
      this.removeAttribute('data-employee-id'); // Reset the data-employee-id attribute when modal is hidden
  });
}
  
  // handleMentorChange()
  // Handles changes to the 'Mentor' checkbox to dynamically update related form fields.
  // Adjusts form fields to either show mentor or mentee options based on the checkbox state.
    function handleMentorChange() {
    console.log("Handling mentor change...");
    const isMentor = document.getElementById('newEmployeeIsMentor').checked;
    console.log("Is Mentor Checked: ", isMentor);
    const assignmentsDropdown = document.getElementById('newMentorAssignments');

    assignmentsDropdown.innerHTML = ''; // Clear existing options
    if (isMentor) {
      // Populate with mentees if this new employee is a mentor
      fetchUnassignedMentees().then(mentees => {
        console.log("Fetched Unassigned Mentees: ", mentees);
        mentees.forEach(mentee => {
          const option = new Option(mentee.name, mentee.employeeId);
          assignmentsDropdown.appendChild(option);
          console.log("Option: ", option);
          console.log("Assignments Dropdown: ", assignmentsDropdown);
        });
      }).catch(error => console.error('Error fetching mentees:', error));
    } else {
      // Populate with mentors if this new employee is a mentee
      fetchMentors().then(mentors => {
        console.log("Fetched Mentors: ", mentors);
        mentors.forEach(mentor => {
          const option = new Option(mentor.name, mentor.employeeId);
          assignmentsDropdown.appendChild(option);
        });
      }).catch(error => console.error('Error fetching mentors:', error));
    }
  }

// Submits new or edited employee data to the server
function handleSubmitEmployee(event) {
    event.preventDefault();
    console.log("Submitting employee form...");
    const isEdit = event.target.id === 'editEmployeeForm';

    console.log("Submitting employee form...", isEdit ? 'Editing' : 'Creating new employee');
  
    const employeeData = prepareEmployeeData(isEdit);
    if (!employeeData) {
        console.error("Form validation failed. Required elements not found.");
        return;
    }
  
    const url = isEdit ? `http://localhost:8080/newhireinfo/${employeeData.employeeId}` : 'http://localhost:8080/newhireinfo';
    console.log("Posting data to:", url);
    fetch(url, {
        method: isEdit ? 'PUT' : 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(employeeData)
    })
    .then(response => response.json().then(data => {
        if (response.ok) {
            console.log("Employee data submitted successfully.", data);
            fetchAllEmployees();
            $(isEdit ? '#editEmployeeModal' : '#newEmployeeModal').modal('hide');
            event.target.reset();
        } else {
            console.error("Failed to submit employee data:", data.message || 'Unknown error');
        }
    }))
    .catch(error => console.error('Error submitting employee data:', error));
}

// Prepares and validates employee data from the form for submission.
function prepareEmployeeData(isEdit) {
    console.log("Preparing employee data for submission...");
    const suffix = isEdit ? 'edit' : 'new';
      const employeeId = isEdit ? document.getElementById('editEmployeeId').value : null;
      const name = document.getElementById(`${suffix}EmployeeName`).value;
      const email = document.getElementById(`${suffix}EmployeeEmail`).value;
      const isMentor = document.getElementById(`${suffix}EmployeeIsMentor`).checked;
      const employmentType = document.getElementById(`${suffix}EmployeeEmploymentType`).value;
      const username = document.getElementById(`${suffix}EmployeeUsername`).value;
      const password = document.getElementById(`${suffix}EmployeePassword`).value;
        const mentorOrMenteeId = document.getElementById(`${suffix}MentorAssignments`).value;
      console.log("Employee data that is ff:", employeeId, name, email, isMentor, employmentType, username, password, mentorOrMenteeId);
  
    // Validation
    if (!name || !email || !employmentType || !username || (isEdit && !employeeId)) {
        console.error("Validation failed. Missing required fields.");
        return null;
    }

    const employeeData = {
        employeeId, name, email, isMentor, employmentType, username, passwordHash: password
    };

    if (mentorOrMenteeId) {
        employeeData.mentorOrMenteeId = mentorOrMenteeId;
    }

    return employeeData;
}

  // handleTableClick(event)
  // Deals with click events on the employee table, distinguishing between view, edit, and archive actions based on button classes.
  // Calls respective functions like viewEmployee, editEmployee, or archiveEmployee.
  function handleTableClick(event) {
    const button = event.target.closest('.btn');
    if (!button) {
        console.error('No button clicked');
        return;
    }
    const employeeId = button.closest('tr').getAttribute('data-employee-id');
    console.log("Clicked on employee table:", employeeId);

    if (button.classList.contains('view-btn')) {
        viewEmployee(employeeId);
    } else if (button.classList.contains('edit-btn')) {
        editEmployee(employeeId);
    } else if (button.classList.contains('delete-btn')) {
        const deleteModal = document.getElementById('deleteEmployeeModal');
        deleteModal.setAttribute('data-employee-id', employeeId);
        new bootstrap.Modal(deleteModal).show();
    }
  }



// Show the edit modal and load the employee data
function showEditModal(employeeId) {
    const editModal = new bootstrap.Modal(document.getElementById('editEmployeeModal'));
    editModal.show(); // This ensures the modal is shown
    console.log("Showing edit modal for employee ID:", employeeId);
    editEmployee(employeeId);
}

// fetchAllEmployees()
// Fetches and displays all employees from the server.
// Populates the employee table with fetched data.
function fetchAllEmployees() {
    console.log("Fetching all employees from the server...");
    fetch('http://localhost:8080/newhireinfo')
    .then(response => response.json())
    .then(employees => {
        const employeeTableBody = document.getElementById('employeeData');
        employeeTableBody.innerHTML = ''; // Clear existing rows
        employees.forEach(employee => {
            const row = document.createElement('tr');
            row.setAttribute('data-employee-id', employee.employeeId);
            row.innerHTML = `
                <td>${employee.employeeId}</td>
                <td>${employee.name}</td>
                <td>${employee.employmentType}</td>
                <td>${employee.isMentor ? 'Yes' : 'No'}</td>
                <td>
                    <button class="btn btn-success view-btn"><i class="bi bi-eye"></i></button>
                    <button class="btn btn-primary edit-btn"><i class="bi bi-pencil-square"></i></button>
                    <button class="btn btn-danger delete-btn"><i class="bi bi-trash"></i></button>
                </td>
            `;
            employeeTableBody.appendChild(row);
        });
        console.log("Employee table populated.");
    })
    .catch(error => console.error('Error fetching employees:', error));
  }

// function populateEmployeeTable(employees) {
//   const tableBody = document.getElementById('employeeData');
//   tableBody.innerHTML = ''; // Clear existing entries

//         employees.forEach(employee => {
//             const row = document.createElement('tr');
//       row.innerHTML = `<td>${employee.employeeId}</td>
//                 <td>${employee.name}</td>
//                 <td>${employee.employmentType}</td>
//                 <td>${employee.isMentor ? 'Yes' : 'No'}</td>
//                 <td>
//                            <button class="btn btn-success view-btn">View</button>
//                            <button class="btn btn-primary edit-btn">Edit</button>
//                            <button class="btn btn-danger delete-btn" data-employee-id="${employee.employeeId}">Delete</button>
//                        </td>`;
//       tableBody.appendChild(row);
//         });
//         console.log("Employee table populated.");
//     })
//     .catch(error => console.error('Error fetching employees:', error));
//   }

// createEmployee(formData)
// Sends a request to the server to create a new employee with provided form data.
// Handles server response and updates UI accordingly (refreshes employee list, etc.).
function createEmployee(employeeData) {
    // Get values from the form
    const isMentor = document.getElementById('newEmployeeIsMentor').checked;
    const mentorSelect = document.getElementById('mentorSelect');
      if (mentorSelect) {
        employeeData.mentor = mentorSelect.value;
      } else {
        console.error('Mentor select box not found');
      }
    const menteeSelect = document.getElementById('menteeSelect');
  
    // Set mentor or mentee ID based on the role of the new hire
    if (isMentor && menteeSelect) {
      employeeData.mentee = menteeSelect.value; // New hire is a mentor and assigns a mentee
    } else if (!isMentor && mentorSelect) {
      employeeData.mentor = mentorSelect.value; // New hire is a mentee and assigns a mentor
    }
    console.log("Sending data to server for new employee creation:", employeeData);
    fetch('http://localhost:8080/newhireinfo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(employeeData)
    })
    .then(response => {
        if (response.ok) {
            onsole.log("Employee created successfully with ID:", data.employeeId);
            fetchAllEmployees(); // Refresh the employee list
            document.getElementById('newEmployeeForm').reset(); // Reset the form
            $('#newEmployeeModal').modal('hide'); // Close the modal
        } else {
            response.json().then(data => {
                console.log('Failed to create employee:', data.message);
                alert('Failed to create employee: ' + data.message);
            });
        }
    })
    .catch(error => console.error('Error creating employee:', error));
  }

// viewEmployee(employeeId)
// Fetches detailed information about a specific employee for viewing.
// Displays employee details in a modal or dedicated view section.
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
          document.getElementById('viewUsername').textContent = employee.developer.username;
            document.getElementById('viewEmail').textContent = employee.developer.email;

          // Populate mentor assignments and tasks for viewing
          populateMentorAssignments(employeeId);
          populateTasks(employeeId);

          // Show the view modal
          $('#viewEmployeeModal').modal('show');
      })
      .catch(error => console.error('Error fetching employee details:', error));
}
window.viewEmployee = viewEmployee;

// editEmployee(employeeId, formData)
// Sends updated employee information to the server.
// Handles the server response to confirm the update and refreshes the displayed data.
function editEmployee(employeeId) {
    console.log(`Starting to edit employee with ID: ${employeeId}`);

    fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
    .then(response => {
        console.log("Fetch response received for edit", response);
        if (response.ok) {
            return response.json();
        } else {
            console.error("Failed to fetch employee data for edit with response status: ", response.status);
            throw new Error('Failed to fetch employee data');
        }
    })
    .then(employee => {
        console.log("Employee data retrieved for edit: ", employee);

        const editModal = $('#editEmployeeModal');
        console.log("Edit modal element:", editModal.length > 0 ? "Found" : "Not Found");

        const nameInput = document.getElementById('editEmployeeName');
        console.log("Name input element:", nameInput ? "Found" : "Not Found");
        if (nameInput) {
            nameInput.value = employee.name;
        }

        const emailInput = document.getElementById('editEmployeeEmail');
        console.log("Email input element:", emailInput ? "Found" : "Not Found");
        if (emailInput) {
            emailInput.value = employee.email;
        }

        const isMentorCheckbox = document.getElementById('editEmployeeIsMentor');
        console.log("Is Mentor checkbox element:", isMentorCheckbox ? "Found" : "Not Found");
        if (isMentorCheckbox) {
            isMentorCheckbox.checked = employee.isMentor;
        }

        const employmentTypeSelect = document.getElementById('editEmployeeEmploymentType');
        console.log("Employment Type select element:", employmentTypeSelect ? "Found" : "Not Found");
        if (employmentTypeSelect) {
            employmentTypeSelect.value = employee.employmentType;
        }

        if (editModal.length) {
            editModal.modal('show');
            console.log("Edit modal shown.");
        } else {
            console.error("Edit modal element not found in the DOM.");
        }
    })
    .catch(error => {
        console.error('Error fetching or processing employee data:', error);
    });
}

window.editEmployee = editEmployee;

// const deleteModal = document.getElementById('deleteEmployeeModal');
// deleteModal.addEventListener('show.bs.modal', function (event) {
//     const employeeId = this.getAttribute('data-employee-id'); // Retrieve the ID set by handleTableClick
//     document.getElementById('confirmDelete').onclick = function () {
//         deleteEmployee(employeeId);
//     };
// });
// deleteModal.addEventListener('hidden.bs.modal', function () {
//     this.removeAttribute('data-employee-id'); // Clean up after hiding
// });

// deleteEmployee(employeeId)
// Similar to archiveEmployee, but specifically sends a deletion request.
// Optionally confirms with the user before deletion.
// Function to delete an employee
function deleteEmployee(event) {
  const deleteModal = document.getElementById('deleteEmployeeModal');
  const employeeId = deleteModal.getAttribute('data-employee-id');

  console.log(`Preparing to delete employee with ID: ${employeeId}`);
  if (confirm('Are you sure you want to delete this employee?')) {
      console.log(`Deleting employee with ID: ${employeeId}`);

      fetch(`http://localhost:8080/newhireinfo/${employeeId}`, { method: 'DELETE' })
          .then(response => {
              if (response.ok) {
                  console.log("Employee deleted successfully");
                  fetchAllEmployees(); // Refresh the employee list
              } else {
                  console.error(`Failed to delete employee. Status: ${response.status}`);
                  response.text().then(text => alert(`Failed to delete employee: ${text}`));
              }
          })
          .catch(error => {
              console.error('Error deleting employee:', error);
              alert('An error occurred while deleting the employee.');
          });
  }
}


// Additional Helper Functionsdown

// fetchMentors()
// Fetches mentor list from the server and populates the mentor dropdown.
function fetchMentors() {
  fetch('http://localhost:8080/newhireinfo/fetchMentors')
  .then(response => response.json())
  .then(mentors => {
      const mentorSelect = document.getElementById('newMentorAssignments');
      mentors.forEach(mentor => {
          const option = document.createElement('option');
          option.value = mentor.employeeId;
          option.textContent = mentor.name;
          mentorSelect.appendChild(option);
      });
      // Optionally auto-select a default mentor if required
      // mentorSelect.value = 'defaultMentorId';
  })
  .catch(error => console.error('Error fetching mentors:', error));
}

// fetchMentees()
// Retrieves mentee list from the server and updates the mentee dropdown.
function fetchUnassignedMentees() {
  fetch('http://localhost:8080/newhireinfo/fetchMentees')
    .then(response => response.json())
    .then(mentees => {
      const menteeSelect = document.getElementById('newMentorAssignments');
      mentees.forEach((mentee, index) => {
        const option = document.createElement('option');
        option.value = mentee.employeeId;
        option.textContent = mentee.name;
        option.selected = index === 0; // Auto-select the first unassigned mentee
        menteeSelect.appendChild(option);
      });
    })
    .catch(error => console.error('Error fetching mentees:', error));
}

// populateMentorAssignments(employeeId)
// Fetches and displays mentor or mentee assignments for a specific employee.
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
            console.log('Mentees:', mentees);
            const viewMentorAssignments = document.getElementById('viewMentorAssignments');
            viewMentorAssignments.textContent = ''; // Clear existing list items

            mentees.forEach(mentee => {
              const li = document.createElement('li');
              li.textContent = `Mentee: ${mentee.name} - ${mentee.employmentType} `;
              viewMentorAssignments.appendChild(li);
            });            
          });
      } else {
        // If the employee is a mentee, note their mentor
        fetch(`http://localhost:8080/newhireinfo/${employeeId}/mentor`)
          .then(response => response.json())
          .then(mentors => {
            console.log('Mentors:', mentors);
            const viewMentorAssignments = document.getElementById('viewMentorAssignments');
            viewMentorAssignments.textContent = ''; // Clear existing list items

            mentors.forEach(mentor => {
              const li = document.createElement('li');
              li.textContent = `Mentor: ${mentor.name} - ${mentor.employmentType} `;
              viewMentorAssignments.appendChild(li);
            });            
          });
      }
    });
}

// populateTasks(employeeId)
// Fetches and shows tasks assigned to an employee, differentiating by mentor or mentee roles.
function populateTasks(employeeId) {
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
  .then(response => response.json())
  .then(employee => {
    const mentorAssignmentsSelect = document.getElementById('viewMentorAssignments');
    mentorAssignmentsSelect.innerHTML = ''; // Clear existing options
  //if the employee is a mentor, fetch and list their tasks
  if (employee.isMentor) {
  fetch(`http://localhost:8080/peercodingtasks/mentor/${employeeId}/tasks`)
      .then(response => response.json())
      .then(tasks => {
        console.log('Tasks:', tasks);
        const tasksList = document.getElementById('viewTasks');
        tasksList.innerHTML = ''; // Clear existing list items
  
        tasks.forEach(task => {
          const li = document.createElement('li');
          li.innerHTML = `Assignment: ${task.assigneeName}, Task ID: ${task.taskId}, Task URL: <a href="${task.taskUrl}" target="_blank">${task.taskUrl}</a>, Task Type: ${task.taskType}, Total Hours: ${task.totalHours}`;
          tasksList.appendChild(li);
        });
      });
    } else {
      // If the employee is a mentee list their tasks
      fetch(`http://localhost:8080/peercodingtasks/by-assignee/${employeeId}`)
      .then(response => response.json())
      .then(tasks => {
        console.log('Tasks:', tasks);
        const tasksList = document.getElementById('viewTasks');
        tasksList.innerHTML = ''; // Clear existing list items
  
        tasks.forEach(task => {
          const li = document.createElement('li');
          li.innerHTML = `Task ID: ${task.taskId}, Task URL: <a href="${task.taskUrl}" target="_blank">${task.taskUrl}</a>, Task Type: ${task.taskType}, Total Hours: ${task.totalHours}`;
          tasksList.appendChild(li);
        });
      });
      
    }
  });    
}

// Add this event listener when the 'New Employee' modal is shown
$('#newEmployeeModal').on('show.bs.modal', function () {
  // Reset and populate the mentor dropdown if the employee is not a mentor
  const mentorSelect = document.getElementById('newMentorAssignments');
  mentorSelect.innerHTML = '<option value="">Select Mentor</option>';
  if (!document.getElementById('newEmployeeIsMentor').checked) {
    fetchMentors(); // This should fetch and populate the mentor dropdown
  }
});

// Modal initialization
// This should be triggered when the edit button is clicked and the modal is about to be shown.
// $('#editEmployeeModal').on('show.bs.modal', function (event) {
//     var button = $(event.relatedTarget); // Button that triggered the modal
//     var employeeId = button.data('employee-id'); // Extract info from data-* attributes
//     console.log("Modal is being triggered for employee ID:", employeeId);
//     $(this).data('employeeId', employeeId); // Set employeeId to the modal for later use
//     editEmployee(employeeId);
//   });