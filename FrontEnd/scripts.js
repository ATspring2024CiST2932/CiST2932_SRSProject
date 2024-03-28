// Function for sorting table
function sortTable(columnIndex) {
    console.log(`Sorting column ${columnIndex}`);
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("tasksTable");
    switching = true;
    dir = "asc"; // Set the sorting direction to ascending by default
  
    while (switching) {
      switching = false;
      rows = table.getElementsByTagName("tr");
  
      for (i = 1; i < (rows.length - 1); i++) {
        shouldSwitch = false;
        x = rows[i].getElementsByTagName("td")[columnIndex];
        y = rows[i + 1].getElementsByTagName("td")[columnIndex];
  
        if (dir == "asc") {
          if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
            shouldSwitch = true;
            break;
          }
        } else if (dir == "desc") {
          if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
            shouldSwitch = true;
            break;
          }
        }
      }
  
      if (shouldSwitch) {
        rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
        switching = true;
        switchcount++;
      } else {
        if (switchcount == 0 && dir == "asc") {
          dir = "desc";
          switching = true;
        }
      }
    }
  }
  
  // Function for filtering table
  function filterTable() {
    var input, filter, table, tr, td, i, j, txtValue;
    input = document.getElementById("filterInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("tasksTable");
    tr = table.getElementsByTagName("tr");
    for (i = 1; i < tr.length; i++) {
      let found = false;
      td = tr[i].getElementsByTagName("td");
      for (j = 0; j < td.length; j++) {
        if (td[j]) {
          txtValue = td[j].textContent || td[j].innerText;
          if (txtValue.toUpperCase().indexOf(filter) > -1) {
            found = true;
            break;
          }
        }
      }
      if (found) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
  
  // Fetch and populate tasks, and handle double-click editing
  fetch('http://localhost:8080/peercodingtasks')
    .then(response => response.json())
    .then(data => {
      const tableBody = document.getElementById('taskRows');
      data.forEach(task => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${task.taskId}</td>
          <td>${task.taskUrl}</td>
          <td>${task.taskNumber}</td>
          <td>${task.taskType}</td>
          <td>${task.totalHours}</td>
          <td>${task.assigneeName}</td>
        `;
        tableBody.appendChild(row);
  
        // Add double-click event listener to each row
        row.addEventListener('dblclick', () => {
            console.log(`Editing task ${task.taskId}`);
            // Populate the edit form with task data
          document.getElementById('editTaskId').value = task.taskId;
          document.getElementById('editTaskUrl').value = task.taskUrl;
          document.getElementById('editTaskNumber').value = task.taskNumber;
          document.getElementById('editTaskType').value = task.taskType;
          document.getElementById('editTotalHours').value = task.totalHours;
          document.getElementById('editAssigneeName').value = task.assigneeName;
  
          // Show the edit task modal
          $('#editTaskForm').modal('show');
        });
      });
    })
    .catch(error => console.error('Error fetching tasks:', error));
  
  // Event listener for the edit form submission
  document.getElementById('editTaskForm').addEventListener('submit', function(event) {
    event.preventDefault();
  
    const taskId = document.getElementById('editTaskId').value;
    const taskData = {
      taskUrl: document.getElementById('editTaskUrl').value,
      taskNumber: document.getElementById('editTaskNumber').value,
      taskType: document.getElementById('editTaskType').value,
      totalHours: document.getElementById('editTotalHours').value,
      assigneeName: document.getElementById('editAssigneeName').value,
    };
  
    // Send PUT request to update the task
    fetch(`http://localhost:8080/peercodingtasks/${taskId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(taskData)
    })
    .then(response => {
      if (response.ok) {
        console.log('Task updated successfully');
        // Optionally, refresh the page to see the updated task in the table
        location.reload();

        // Hide the modal
        $('#editTaskForm').modal('hide');

        // Clear the form
        document.getElementById('editTaskForm').reset();
        } else {

        console.error('Error updating task');
        alert('Error updating task');
        }
        })
        .catch(error => console.error('Error updating task:', error));
        }
        );

        function sortTable(columnIndex) {
            console.log(`Sorting column ${columnIndex}`);
            // rest of the function...
          }

        function filterTable() {
            // rest of the function...
        }

        row.addEventListener('dblclick', () => {
            console.log(`Editing task ${task.taskId}`);
            // rest of the event listener code...
          });

          // Populate the form with the data of the task to be edited
        document.getElementById("editTaskUrl").value = task.taskUrl;
        document.getElementById("editTaskNumber").value = task.taskNumber;
        document.getElementById("editTaskType").value = task.taskType;
        document.getElementById("editTotalHours").value = task.totalHours;
        document.getElementById("editAssigneeName").value = task.assigneeName;

        // Collect the data from the form to send to the server
        const updatedTask = {
        taskUrl: document.getElementById("editTaskUrl").value,
        taskNumber: document.getElementById("editTaskNumber").value,
        taskType: document.getElementById("editTaskType").value,
        totalHours: document.getElementById("editTotalHours").value,
        assigneeName: document.getElementById("editAssigneeName").value
        };


          document.querySelectorAll('#tasksTable tbody tr').forEach(row => {
            row.addEventListener('click', function() {
                // Your code to open the modal and populate it with task details
            });
        });
        

          // Function for sorting table
          function sortTable(columnIndex) {
            var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
            table = document.getElementById("tasksTable");
            switching = true;
            dir = "asc"; // Set the sorting direction to ascending by default
        
            while (switching) {
              switching = false;
              rows = table.getElementsByTagName("tr");
        
              for (i = 1; i < (rows.length - 1); i++) {
                shouldSwitch = false;
                x = rows[i].getElementsByTagName("td")[columnIndex];
                y = rows[i + 1].getElementsByTagName("td")[columnIndex];
        
                if (dir == "asc") {
                  if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    shouldSwitch = true;
                    break;
                  }
                } else if (dir == "desc") {
                  if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    shouldSwitch = true;
                    break;
                  }
                }
              }
        
              if (shouldSwitch) {
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
                switchcount++;
              } else {
                if (switchcount == 0 && dir == "asc") {
                  dir = "desc";
                  switching = true;
                }
              }
            }
          }
        
          // Function for filtering table
          function filterTable() {
            var input, filter, table, tr, td, i, j, txtValue;
            input = document.getElementById("filterInput");
            filter = input.value.toUpperCase();
            table = document.getElementById("tasksTable");
            tr = table.getElementsByTagName("tr");
            for (i = 1; i < tr.length; i++) {
              let found = false;
              td = tr[i].getElementsByTagName("td");
              for (j = 0; j < td.length; j++) {
                if (td[j]) {
                  txtValue = td[j].textContent || td[j].innerText;
                  if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    found = true;
                    break;
                  }
                }
              }
              if (found) {
                tr[i].style.display = "";
              } else {
                tr[i].style.display = "none";
              }
            }
          }
        
          // Fetch and populate tasks, and handle double-click editing
          fetch('http://localhost:8080/peercodingtasks')
            .then(response => response.json())
            .then(data => {
              const tableBody = document.getElementById('taskRows');
              data.forEach(task => {
                const row = document.createElement('tr');
                row.innerHTML = `
                  <td>${task.taskId}</td>
                  <td>${task.taskUrl}</td>
                  <td>${task.taskNumber}</td>
                  <td>${task.taskType}</td>
                  <td>${task.totalHours}</td>
                  <td>${task.assigneeName}</td>
                `;
                tableBody.appendChild(row);
        
                // Add double-click event listener to each row
                row.addEventListener('dblclick', () => {
                  // Populate the edit form with task data
                  document.getElementById('editTaskId').value = task.taskId;
                  document.getElementById('editTaskUrl').value = task.taskUrl;
                  document.getElementById('editTaskNumber').value = task.taskNumber;
                  document.getElementById('editTaskType').value = task.taskType;
                  document.getElementById('editTotalHours').value = task.totalHours;
                  document.getElementById('editAssigneeName').value = task.assigneeName;
        
                  // Show the edit task modal
                  $('#editTaskForm').modal('show');
                });
              });
            })
            .catch(error => console.error('Error fetching tasks:', error));
        
          // Event listener for the edit form submission
          document.getElementById('editTaskForm').addEventListener('submit', function(event) {
            event.preventDefault();
        
            const taskId = document.getElementById('editTaskId').value;
            const taskData = {
              taskUrl: document.getElementById('editTaskUrl').value,
              taskNumber: document.getElementById('editTaskNumber').value,
              taskType: document.getElementById('editTaskType').value,
              totalHours: document.getElementById('editTotalHours').value,
              assigneeName: document.getElementById('editAssigneeName').value,
            };
        
            // Send PUT request to update the task
            fetch(`http://localhost:8080/peercodingtasks/${taskId}`, {
              method: 'PUT',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(taskData)
            })
            .then(response => {
              if (response.ok) {
                console.log('Task updated successfully');
                // Optionally, refresh the page or update the UI to show the updated task
              } else {
                console        .error('Failed to update task');
            }
          })
          .catch(error => {
            console.error('Error:', error);
          });
        });
   
      
        
          

          
  