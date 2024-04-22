document.addEventListener('DOMContentLoaded', function() {
    const taskButton = document.getElementById('taskForm');  // Corrected ID

    if (taskButton) {
        taskButton.addEventListener('click', function() {
            console.log("Task button clicked");
            let modalExists = document.getElementById('newTaskModal');
            if (!modalExists) {
                console.log("Modal does not exist, creating new modal");
                const modalHtml = `
                    <div class="modal fade" id="newTaskModal" tabindex="-1" aria-labelledby="newTaskModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Complete Task Form</h4>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                               <div class="modal-body">
                                    <form id="taskFormElement" method="post">
                                        <!-- Form content here -->
                                        <div class="mb-3">
                                            <label for="taskUrl" class="form-label">Task URL:</label>
                                            <input type="text" id="taskUrl" name="taskUrl" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="taskNumber" class="form-label">Task Number:</label>
                                            <input type="text" id="taskNumber" name="taskNumber" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="taskType" class="form-label">Task Type:</label>
                                            <input type="text" id="taskType" name="taskType" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="totalHours" class="form-label">Total Hours:</label>
                                            <input type="number" step="0.1" id="totalHours" name="totalHours" class="form-control" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="assigneeName" class="form-label">Assignee:</label>
                                            <select id="assigneeName" name="assigneeName" class="form-control" required>
                                            <option value="">Select Assignee</option>
                                            </select>
                                        </div>
                                        <button type="submit" class="btn btn-primary">Submit</button>
                                        </form>
                  </div>
                </div>
              </div>
            </div>
          `;
          document.body.insertAdjacentHTML('beforeend', modalHtml);
          modalExists = new bootstrap.Modal(document.getElementById('newTaskModal'));
          console.log("Modal created and initialized");

          attachTaskFormSubmission(document.getElementById('taskFormElement'));
          fetchAssignees();
      }
      modalExists.show();
      console.log("Modal displayed");
  });
} else {
  console.error('Task button not found');
}
});

function attachTaskFormSubmission(formElement) {
    console.log("Attaching form submission event listener");
    formElement.addEventListener('submit', function(event) {
        event.preventDefault();
        console.log("Form submission triggered");
        const formData = new FormData(formElement);
        const taskData = {};
        formData.forEach((value, key) => taskData[key] = value);
        console.log("Form data collected:", taskData);
        submitTaskData(taskData);
    });
  }

  function fetchAndDisplayAssigneeName(employeeId) {
    logInfo("Fetching assignee name for employee", { employeeId });
    fetch(`http://localhost:8080/getAssigneeName/${employeeId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch assignee name');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('assigneeName').value = data.name;
            logInfo("Assignee name loaded", { employeeId, name: data.name });
        })
        .catch(error => {
            logError("Error fetching assignee name", error);
        });
}

function openFormWithEmployeeContext(source, employeeId) {
    logInfo("Opening form from", { source, employeeId });
    fetchAndDisplayAssigneeName(employeeId);
    $('#employeeFormModal').modal('show');
}
  
  function fetchAssignees() {
    console.log("Fetching assignees");
    const assigneeSelect = document.getElementById('assigneeName');
    if (assigneeSelect) {
        fetch('http://localhost:8080/newhireinfo/names')
            .then(response => response.json())
            .then(names => {
                names.forEach(name => {
                    const option = document.createElement('option');
                    option.value = name;
                    option.text = name;
                    assigneeSelect.appendChild(option);
                });
                console.log("Assignees fetched and populated");
            })
            .catch(error => {
                console.error('Error fetching assignees:', error);
            });
    } else {
        console.error("Assignee select element not found");
    }
  }
  
  function submitTaskData(taskData) {
    console.log("Submitting task data to server:", taskData);
    fetch('http://localhost:8080/peercodingtasks', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(taskData)
    })
    .then(response => {
        if (response.ok) {
            console.log('Task added successfully');
            document.getElementById('taskFormElement').reset();
            var modal = bootstrap.Modal.getInstance(document.getElementById('newTaskModal'));
            modal.hide();
        } else {
            console.error('Failed to add task');
        }
    })
    .catch(error => {
        console.error('Error submitting task data:', error);
    });
  }
  