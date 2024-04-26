document.addEventListener('DOMContentLoaded', function() {
  initializeFormDefaults();
  attachEventListeners();
  fetchAllEmployees();
  populateMentorsDropdown(); // Automatically populate mentors on page load
});

function initializeFormDefaults() {
  console.log("Initializing form defaults...");
  document.getElementById('newEmployeeName').value = '';
  document.getElementById('newEmployeeEmail').value = '';
  document.getElementById('newEmployeeIsMentor').checked = false;
  document.getElementById('newEmployeeUsername').value = '';
  document.getElementById('newEmployeePassword').value = '';
  document.getElementById('newEmployeeEmploymentType').value = '';
}

function attachEventListeners() {
  document.getElementById('newEmployeeIsMentor').addEventListener('change', handleMentorChange);
  document.getElementById('addEmployeeForm').addEventListener('submit', handleSubmitEmployee);
  document.getElementById('editEmployeeForm').addEventListener('submit', handleSubmitEmployee);
  document.getElementById('employeeTable').addEventListener('click', handleTableClick);
}

function handleMentorChange() {
  const isMentor = document.getElementById('newEmployeeIsMentor').checked;
  const dropdown = document.getElementById('newMentorAssignments');
  dropdown.innerHTML = ''; // Clear the dropdown

  if (isMentor) {
      fetchUnassignedMentees().then(mentees => {
          populateDropdown(dropdown, mentees);
      });
  } else {
      fetchMentors().then(mentors => {
          populateDropdown(dropdown, mentors);
      });
  }
}

function fetchMentors() {
  return fetch('http://localhost:8080/newhireinfo/fetchMentors')
      .then(response => response.json())
      .catch(error => console.error('Error fetching mentors:', error));
}

function fetchUnassignedMentees() {
  return fetch('http://localhost:8080/newhireinfo/fetchMentees')
      .then(response => response.json())
      .catch(error => console.error('Error fetching mentees:', error));
}

function populateDropdown(dropdown, data) {
  data.forEach(item => {
      const option = new Option(item.name, item.employeeId);
      dropdown.appendChild(option);
  });
}

function populateMentorsDropdown() {
  fetchMentors().then(mentors => {
      const mentorDropdown = document.getElementById('newMentorAssignments');
      populateDropdown(mentorDropdown, mentors);
  });
}

function handleSubmitEmployee(event) {
  event.preventDefault();
  const form = event.target;
  const formData = new FormData(form);
  const data = Object.fromEntries(formData.entries());
  
  // Determine if we are creating a new employee or updating an existing one
  const isNew = !formData.get('employeeId');
  const endpoint = isNew ? 'http://localhost:8080/newhireinfo' : `http://localhost:8080/newhireinfo/${formData.get('employeeId')}`;
  const method = isNew ? 'POST' : 'PUT';

  fetch(endpoint, {
      method: method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
  }).then(response => {
      if (response.ok) {
          return response.json();
      } else {
          throw new Error('Failed to submit employee data');
      }
  }).then(data => {
      console.log("Success:", data);
      fetchAllEmployees(); // Refresh the list
      form.reset(); // Reset the form
      if (isNew) {
          document.getElementById('newEmployeeModal').style.display = 'none';
      } else {
          document.getElementById('editEmployeeModal').style.display = 'none';
      }
  }).catch(error => console.error('Error:', error));
}

function fetchAllEmployees() {
  fetch('http://localhost:8080/newhireinfo')
      .then(response => response.json())
      .then(data => {
          populateEmployeeTable(data);
      })
      .catch(error => console.error('Error fetching employees:', error));
}

function populateEmployeeTable(employees) {
  const tableBody = document.getElementById('employeeData');
  tableBody.innerHTML = ''; // Clear existing entries

  employees.forEach(employee => {
      const row = document.createElement('tr');
      row.innerHTML = `<td>${employee.employeeId}</td>
                       <td>${employee.name}</td>
                       <td>${employee.employmentType}</td>
                       <td>${employee.isMentor ? 'Yes' : 'No'}</td>
                       <td>
                           <button class="btn btn-success view-btn">View</button>
                           <button class="btn btn-primary edit-btn">Edit</button>
                           <button class="btn btn-danger delete-btn" data-employee-id="${employee.employeeId}">Delete</button>
                       </td>`;
      tableBody.appendChild(row);
  });
}

function handleTableClick(event) {
  const employeeId = event.target.closest('tr').getAttribute('data-employee-id');
  if (event.target.classList.contains('delete-btn')) {
      deleteEmployee(employeeId);
  } else if (event.target.classList.contains('edit-btn')) {
      const form = document.getElementById('editEmployeeForm');
      fetchAndPopulateForm(employeeId, form);
      document.getElementById('editEmployeeModal').style.display = 'block';
  } else if (event.target.classList.contains('view-btn')) {
      viewEmployeeDetails(employeeId);
  }
}

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

function viewEmployeeDetails(employeeId) {
  console.log(`Viewing employee with ID: ${employeeId}`);
  fetch(`http://localhost:8080/newhireinfo/${employeeId}/details`)
      .then(response => {
          if (!response.ok) {
              throw new Error('Network response was not ok ' + response.statusText);
          }
          return response.json();
      })
      .then(employee => {
          // Populate the view modal with the employee's details
          document.getElementById('viewEmployeeId').textContent = employee.employeeId;
          document.getElementById('viewName').textContent = employee.name;
          document.getElementById('viewIsMentor').textContent = employee.isMentor ? "Yes" : "No";
          document.getElementById('viewEmploymentType').textContent = employee.employmentType;
          document.getElementById('viewUsername').textContent = employee.username;
          document.getElementById('viewEmail').textContent = employee.email;

          // Populate mentor or mentee assignments
          const mentorOrMenteeInfo = document.getElementById('viewMentorOrMentee');
          if (employee.isMentor) {
              mentorOrMenteeInfo.textContent = `Mentees: ${employee.assignmentsAsMentorIds.join(", ")}`;
          } else {
              mentorOrMenteeInfo.textContent = `Mentor ID: ${employee.mentorOrMenteeId}`;
          }

          // Populate tasks
          const tasksList = document.getElementById('viewTasks');
          tasksList.innerHTML = '';
          employee.tasks.forEach(task => {
              const taskElement = document.createElement('li');
              taskElement.textContent = `Task ID: ${task.taskId}, URL: ${task.taskUrl}`;
              tasksList.appendChild(taskElement);
          });

          // Show the view modal
          $('#viewEmployeeModal').modal('show');
      })
      .catch(error => console.error('Error fetching employee details:', error));
}


function fetchAndPopulateForm(employeeId, form) {
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
      .then(response => response.json())
      .then(data => {
          form.elements['employeeId'].value = data.employeeId;
          form.elements['name'].value = data.name;
          form.elements['email'].value = data.email;
          form.elements['isMentor'].checked = data.isMentor;
          form.elements['employmentType'].value = data.employmentType;
          form.elements['username'].value = data.username; // Assuming username is part of the data
          form.elements['password'].value = ''; // Password should not be auto-filled
      })
      .catch(error => console.error('Error fetching employee details:', error));
}

function populateMentorMenteeTasks(employee) {
  const mentorList = document.getElementById('viewMentorAssignments');
  const taskList = document.getElementById('viewTasks');
  mentorList.innerHTML = '';
  taskList.innerHTML = '';

  // Assume `employee.mentorAssignments` and `employee.tasks` are arrays containing the necessary info
  employee.mentorAssignments.forEach(assignment => {
      const li = document.createElement('li');
      li.textContent = `Mentee: ${assignment.menteeName}`;
      mentorList.appendChild(li);
  });

  employee.tasks.forEach(task => {
      const li = document.createElement('li');
      li.textContent = `Task: ${task.description}`;
      taskList.appendChild(li);
  });
}
