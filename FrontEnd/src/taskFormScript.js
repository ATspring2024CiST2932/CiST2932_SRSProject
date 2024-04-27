document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("taskFormElement");
    const assigneeSelect = document.getElementById("assigneeName");
  
    // Populate assignee dropdown
    fetch('http://localhost:8080/newhireinfo/names')
      .then(response => response.json())
      .then(names => {
        names.forEach(name => {
          const option = document.createElement("option");
          option.value = name;
          option.text = name;
          assigneeSelect.appendChild(option);
        });
      });
  
    // Handle form submission
    form.addEventListener("submit", function (event) {
      event.preventDefault();
      const formData = new FormData(form);
      const taskData = {};
      formData.forEach((value, key) => {
        taskData[key] = value;
      });
  
      fetch('http://localhost:8080/peercodingtasks', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(taskData)
      })
      .then(response => response.ok ? console.log('Record created successfully') : console.error('Failed to create record'))
      .catch(error => console.error('Error:', error));
    });
  });
  
