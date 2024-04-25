document.addEventListener('DOMContentLoaded', function() {
    console.log("Initializing application...");
    const taskButton = document.getElementById('#newTaskModal');  // Corrected ID for the button to avoid conflicts
    const newTaskAssigneeSelect = document.getElementById("assigneeName");
    populateAssigneeDropdown(newTaskAssigneeSelect);

    if (taskButton) {
        taskButton.addEventListener('click', function() {
            console.log("Task button clicked");
            let modalExists = document.getElementById('newTaskModal');
            if (!modalExists) {
                console.log("Modal does not exist, creating new modal");
                const modalHtml = `
                <div class="modal fade" id="newTaskModal" tabindex="-1" aria-labelledby="newTaskModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="newTaskModalLabel">New Task</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="taskForm"> <!-- Renamed form ID -->
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
                                        </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" form="taskForm" class="btn btn-primary">Save Task</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                `;
                document.body.insertAdjacentHTML('beforeend', modalHtml);
                modalExists = new bootstrap.Modal(document.getElementById('newTaskModal'));
                console.log("Modal created and initialized");

                // Attach form submission and populate dropdown correctly
                const formElement = document.getElementById('taskForm');
                const assigneeSelectElement = document.getElementById("assigneeName");
                attachTaskFormSubmission(formElement);
                populateAssigneeDropdown(assigneeSelectElement);
            }
            modalExists.show();
            console.log("Modal displayed");
        });
    } else {
        console.error('Task button not found');
    }
});
 
function populateAssigneeDropdown(selectElement) {
    fetch('http://localhost:8080/newhireinfo/names')
    .then(response => response.json())
    .then(names => {
        names.forEach(name => {
            const option = document.createElement("option");
            option.value = name;
            option.text = name;
            selectElement.appendChild(option);
        });
    });
}

document.addEventListener('DOMContentLoaded', function() {
    console.log("Application initialized");
  
    // Optional: If you need to populate any dropdowns or perform actions when the modal is about to show
    $('#newTaskModal').on('show.bs.modal', function (event) {
        // This function could populate dropdowns or reset the form, for example
        console.log("Modal is about to be shown");
        // Optional: Populate fields or perform other setup tasks
    });
  
    // Handling form submission within the modal
    const formElement = document.getElementById('taskForm');
    formElement.addEventListener('submit', function(event) {
        event.preventDefault();  // Prevent the default form submission
        console.log("Form submission intercepted");
  
        // Collect data from the form
        const formData = new FormData(formElement);
        const taskData = {};
        formData.forEach((value, key) => {
            taskData[key] = value;
        });
  
        // Perform the fetch request to send data to the server
        fetch('http://your-api-endpoint/tasks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(taskData)
        })
        .then(response => {
            if (response.ok) {
                console.log('Task added successfully');
                formElement.reset();  // Reset the form
                $('#newTaskModal').modal('hide');  // Hide the modal
                // Optionally, refresh part of your page or update UI elements
            } else {
                console.error('Failed to add task');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    });
  });
  
