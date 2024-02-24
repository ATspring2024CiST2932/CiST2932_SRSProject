import React, { useState } from 'react';
import axios from 'axios';
import MentorDropdown from './components/MentorDropdown';


const App = () => {
  const [mentorDetails, setMentorDetails] = useState(null);

  const handleSelectMentor = async (mentorId) => {
    if (mentorId) {
      const response = await axios.get(`/api/mentors/${mentorId}`);
      setMentorDetails(response.data);
    } else {
      setMentorDetails(null);
    }
  };

  return (
    <div>
      <MentorDropdown onSelectMentor={handleSelectMentor} />
      {mentorDetails && (
        <div>
          <h2>Mentor: {mentorDetails.name}</h2>
          <h3>Mentees:</h3>
          <ul>
            {mentorDetails.mentees.map((mentee) => (
              <li key={mentee.id}>{mentee.name}</li>
            ))}
          </ul>
          <h3>Tasks:</h3>
          <ul>
            {mentorDetails.tasks.map((task) => (
              <li key={task.id}>{task.description}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default App;
