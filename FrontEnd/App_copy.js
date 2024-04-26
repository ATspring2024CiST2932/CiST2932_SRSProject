import 'bootstrap/dist/css/bootstrap.min.css';

// Document ready function
document.addEventListener('DOMContentLoaded', function () {
    console.log("Initializing application...");

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
// Attaches event listeners to various elements for interactive functionalities.
function attachEventListeners() {
  console.log("Attaching event listeners...");
  document.getElementById('newEmployeeIsMentor').addEventListener('change', handleMentorChange);
  document.getElementById('addEmployeeForm').addEventListener('submit', handleSubmitEmployee);
  document.getElementById('editEmployeeForm').addEventListener('submit', handleSubmitEmployee);
  document.getElementById('employeeTable').addEventListener('click', handleTableClick);

  // Setup listeners for delete employee modal.
  const deleteModal = document.getElementById('deleteEmployeeModal');
  deleteModal.addEventListener('show.bs.modal', function(event) {
      const button = event.relatedTarget; // Button that triggered the modal
      const employeeId = button.getAttribute('data-employee-id'); // Extract info from data-attributes
      console.log("Preparing to delete employee ID:", employeeId);
      document.getElementById('confirmDelete').onclick = function() {
          deleteEmployee(employeeId);
      };
  });
  deleteModal.addEventListener('hidden.bs.modal', function() {
      this.removeAttribute('data-employee-id');
  });
}


// handleMentorChange()
// Handles changes to the 'Mentor' checkbox to dynamically update related form fields.
// Adjusts form fields to either show mentor or mentee options based on the checkbox state.
function handleMentorChange() {
  console.log("Mentor checkbox state changed.");
  const isMentor = document.getElementById('newEmployeeIsMentor').checked;
  console.log("Is Mentor Checked:", isMentor);
  const assignmentsDropdown = document.getElementById('newMentorAssignments');
  assignmentsDropdown.innerHTML = ''; // Clear existing options

  if (isMentor) {
      fetchMentees().then(mentees => {
          console.log("Populating dropdown with mentees.");
          mentees.forEach(mentee => {
              const option = new Option(mentee.name, mentee.employeeId);
              assignmentsDropdown.appendChild(option);
          });
      }).catch(error => console.error('Error fetching mentees:', error));
  } else {
      fetchMentors().then(mentors => {
          console.log("Populating dropdown with mentors.");
          mentors.forEach(mentor => {
              const option = new Option(mentor.name, mentor.employeeId);
              assignmentsDropdown.appendChild(option);
          });
      }).catch(error => console.error('Error fetching mentors:', error));
  }
}


// Submits new or edited employee data to the server.

function handleSubmitEmployee(event) {
  event.preventDefault();
  console.log("Submitting employee form...");
  const formId = event.target.id;
  const isEdit = formId === 'editEmployeeForm';

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
  .then(response => {
      if (response.ok) {
          console.log("Employee data submitted successfully.");
          fetchAllEmployees(); // Refresh the employee list on successful submit
      } else {
          console.error("Failed to submit employee data.");
          response.json().then(data => console.error('Submission error:', data.message));
      }
  })
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

  // Check for required fields
  if (!name || !email || !employmentType || !username || !password) {
      console.error("Validation failed. Missing required fields.");
      return null;
  }

  return {
      employeeId, name, email, isMentor, employmentType, username, password
  };
}

// handleTableClick(event)
// Deals with click events on the employee table, distinguishing between view, edit, and archive actions based on button classes.
// Calls respective functions like viewEmployee, editEmployee, or archiveEmployee.
function handleTableClick(event) {
  const employeeId = event.target.closest('tr').getAttribute('data-employee-id');
  console.log("Clicked on employee table:", employeeId);

  if (event.target.classList.contains('view-btn')) {
      viewEmployee(employeeId);
  } else if (event.target.classList.contains('edit-btn')) {
      editEmployee(employeeId);
  } else if (event.target.classList.contains('delete-btn')) {
      const deleteModal = document.getElementById('deleteEmployeeModal');
      deleteModal.setAttribute('data-employee-id', employeeId);
      new bootstrap.Modal(deleteModal).show();
  }
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
          row.innerHTML = `
              <td>${employee.employeeId}</td>
              <td>${employee.name}</td>
              <td>${employee.employmentType}</td>
              <td>${employee.isMentor ? 'Yes' : 'No'}</td>
              <td>
                  <button class="btn btn-success view-btn"><i class="bi bi-eye"></i></button>
                  <button class="btn btn-primary edit-btn"><i class="bi bi-pencil-square"></i></button>
                  <button class="btn btn-danger delete-btn" data-bs-toggle="modal" data-bs-target="#deleteEmployeeModal"><i class="bi bi-trash"></i></button>
              </td>
          `;
          employeeTableBody.appendChild(row);
      });
      console.log("Employee table populated.");
  })
  .catch(error => console.error('Error fetching employees:', error));
}

  
  // Event listener for the 'New Employee' form submission
  document.getElementById('newEmployeeForm').addEventListener('submit', function(event) {
    event.preventDefault();
    createEmployee();
  });
  
  // Fetch all employees on page load
  document.addEventListener('DOMContentLoaded', fetchAllEmployees);
  

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

function createOrUpdateEmployee(employeeData, employeeId = null) {
  const isNewEmployee = !employeeId;
  const url = isNewEmployee ? 'http://localhost:8080/newhireinfo' : `http://localhost:8080/newhireinfo/${employeeId}`;
  const method = isNewEmployee ? 'POST' : 'PUT';

  // Logging the data to be sent for visual inspection
  console.log("Prepared data for API request:", JSON.stringify(employeeData, null, 2));
  console.log(`${isNewEmployee ? "Creating" : "Updating"} employee at ${url} using ${method} method.`);

  fetch(url, {
      method: method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(employeeData)
  }).then(response => {
      if (response.ok) {
          console.log(isNewEmployee ? "Employee created successfully" : "Employee updated successfully");
          fetchAllEmployees(); // Refresh the employee list
          document.getElementById(isNewEmployee ? 'newEmployeeModal' : 'editEmployeeModal').modal('hide');
      } else {
          console.error("Failed to submit employee data");
          response.json().then(data => {
              console.error('Error details:', data.message);
          });
      }
  }).catch(error => console.error('Error submitting employee data:', error));
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
    const employee = employees.find(e => e.id === employeeId);
    if (employee) {
      const newName = prompt('Enter new name:', employee.name);
      const newEmail = prompt('Enter new email:', employee.email);
      const newIsMentor = confirm('Is this employee a mentor?');
      const newEmploymentType = prompt('Enter new employment type:', employee.employmentType);
      if (newName && newEmail && newEmploymentType) {
        employee.name = newName;
        employee.email = newEmail;
        employee.isMentor = newIsMentor;
        employee.employmentType = newEmploymentType;
        renderEmployees();
      }
    }
  }
  
  // Function to delete an employee
  function deleteEmployee(employeeId) {
    if (confirm('Are you sure you want to delete this employee?')) {
        console.log(`Deleting employee with ID: ${employeeId}`);
        fetch(`http://localhost:8080/newhireinfo/${employeeId}`, { method: 'DELETE' })
        .then(response => {
            if (response.ok) {
                console.log("Employee deleted successfully");
                fetchAllEmployees(); // Refresh the employee list
            } else {
                alert('Failed to delete employee.');
            }
        })
        .catch(error => console.error('Error deleting employee:', error));
    }
}

  document.getElementById('employeeTable').addEventListener('click', function(event) {
    const target = event.target;
    const row = target.closest('tr');
    if (target.classList.contains('edit-btn')) {
        const employeeId = row.getAttribute('data-employee-id');
        console.log("Preparing to edit employee ID:", employeeId);
        fetchAndPopulateEditForm(employeeId);
    } else if (target.classList.contains('delete-btn')) {
        const employeeId = row.getAttribute('data-employee-id');
        const deleteModal = document.getElementById('deleteEmployeeModal');
        deleteModal.setAttribute('data-employee-id', employeeId);
        new bootstrap.Modal(deleteModal).show();
    }
});

function fetchAndPopulateEditForm(employeeId) {
    // Fetch employee details and populate the edit form
    fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
    .then(response => response.json())
    .then(employee => {
        document.getElementById('editEmployeeName').value = employee.name;
        // Populate other fields
        console.log("Edit form populated for employee ID:", employeeId);
    })
    .catch(error => console.error('Error fetching employee details:', error));
}
