import React, { useState, useEffect } from 'react';
import axios from 'axios';

const MentorDropdown = ({ onSelectMentor }) => {
  const [mentors, setMentors] = useState([]);

  useEffect(() => {
    const fetchMentors = async () => {
      const response = await axios.get('/api/mentors');
      setMentors(response.data);
    };

    fetchMentors();
  }, []);

  return (
    <select onChange={(e) => onSelectMentor(e.target.value)}>
      <option value="">Select a Mentor</option>
      {mentors.map((mentor) => (
        <option key={mentor.id} value={mentor.id}>
          {mentor.name}
        </option>
      ))}
    </select>
  );
};

export default MentorDropdown;
