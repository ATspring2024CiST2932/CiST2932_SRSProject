// Fetch all employees and display them in the table
function fetchAllEmployees() {
    fetch('http://localhost:8080/newhireinfo')
      .then(response => response.json())
      .then(employees => {
        const tableBody = document.getElementById('data');
        tableBody.innerHTML = ''; // Clear existing rows
        employees.forEach(employee => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${employee.employeeId}</td>
            <td>${employee.name}</td>
            <td>${employee.userType}</td>
            <td>${employee.username}</td>
            <td>${employee.password}</td>
            <td>${employee.email}</td>
            <td>${employee.employmentType}</td>
            <td>
              <button class="btn btn-success" onclick="viewEmployee(${employee.employeeId})">View</button>
              <button class="btn btn-primary" onclick="editEmployee(${employee.employeeId})">Edit</button>
              <button class="btn btn-danger" onclick="archiveEmployee(${employee.employeeId})">Delete</button>
            </td>
          `;
          tableBody.appendChild(row);
        });
      })
      .catch(error => console.error('Error fetching employees:', error));
  }
  
  // Function to create a new employee
  function createEmployee() {
    const employeeData = {
      name: document.getElementById('name').value,
      userType: document.querySelector('input[name="userType"]:checked').value,
      username: document.getElementById('username').value,
      password: document.getElementById('password').value,
      email: document.getElementById('email').value,
      employmentType: document.querySelector('input[name="employmentType"]:checked').value,
    };
  
    fetch('http://localhost:8080/newhireinfo', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(employeeData),
    })
    .then(response => {
      if (response.ok) {
        fetchAllEmployees(); // Refresh the employee list
        document.getElementById('newEmployeeForm').reset(); // Reset the form
        $('#userForm').modal('hide'); // Close the modal
      } else {
        alert('Failed to create employee.');
      }
    })
    .catch(error => console.error('Error creating employee:', error));
  }
  
  // Function to view an employee's details
// Function to view an employee's details
function viewEmployee(employeeId) {
  console.log(`Viewing employee with ID: ${employeeId}`);
  fetch(`http://localhost:8080/newhireinfo/${employeeId}`)
    .then(response => response.json())
    .then(employee => {
      // Populate the view modal with the employee's details
      document.getElementById('viewEmployeeId').textContent = employee.employeeId;
      document.getElementById('viewName').textContent = employee.name;
      document.getElementById('viewIsMentor').textContent = employee.isMentor ? 'Yes' : 'No';
      document.getElementById('viewEmploymentType').textContent = employee.employmentType;

      // Populate mentor assignments
      const mentorAssignmentsList = document.getElementById('viewMentorAssignments');
      mentorAssignmentsList.innerHTML = '';
      employee.assignmentsAsMentor.forEach(assignment => {
        const li = document.createElement('li');
        li.textContent = `Assignment ID: ${assignment.assignmentId}, Mentee ID: ${assignment.id}`;
        mentorAssignmentsList.appendChild(li);
      });

      // Populate tasks
      const tasksList = document.getElementById('viewTasks');
      tasksList.innerHTML = '';
      employee.assignedTasks.forEach(task => {
        const li = document.createElement('li');
        li.textContent = `Task ID: ${task.taskId}, Task Type: ${task.taskType}, Total Hours: ${task.totalHours}, Assignee: ${task.assigneeName}`;
        tasksList.appendChild(li);
      });

      // Show the view modal
      $('#viewEmployeeModal').modal('show');
    })
    .catch(error => console.error('Error fetching employee details:', error));
}

  
// Function to edit employee details
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

  // Function to archive an employee
function archiveEmployee(employeeId) {
    const index = employees.findIndex(e => e.id === employeeId);
    if (index !== -1) {
      employees.splice(index, 1);
      renderEmployees();
    }
  }
  
  // Function to delete an employee
  function deleteEmployee(employeeId) {
    if (confirm('Are you sure you want to delete this employee?')) {
      fetch(`http://localhost:8080/newhireinfo/${employeeId}`, {
        method: 'DELETE',
      })
      .then(response => {
        if (response.ok) {
          fetchAllEmployees(); // Refresh the employee list
        } else {
          alert('Failed to delete employee.');
        }
      })
      .catch(error => console.error('Error deleting employee:', error));
    }
  }
  
  // Event listener for the 'New Employee' form submission
  document.getElementById('newEmployeeForm').addEventListener('submit', function(event) {
    event.preventDefault();
    createEmployee();
  });
  
  // Fetch all employees on page load
  document.addEventListener('DOMContentLoaded', fetchAllEmployees);
  