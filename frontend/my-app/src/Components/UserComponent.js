import React, { useState, useEffect } from 'react';
import axios from 'axios';


function UserComponent() {
  const [users, setUsers] = useState([]);
  const [newUser, setNewUser] = useState({ username: '', email: '' });

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    const response = await axios.get('http://localhost:8080/users');
    setUsers(response.data);
  };

  const addUser = async () => {
    await axios.post('http://localhost:8080/users', newUser);
    setNewUser({ username: '', email: '' });
    fetchUsers();
  };

  const deleteUser = async (id) => {
    await axios.delete(`http://localhost:8080/users/${id}`);
    fetchUsers();
  };

  return (
    <div>
      <h2>Users</h2>
      <ul>
        {users.map((user) => (
          <li key={user.id}>
            {user.username} - {user.email}
            <button onClick={() => deleteUser(user.id)}>Delete</button>
          </li>
        ))}
      </ul>
      <h3>Add New User</h3>
      <input
        type="text"
        placeholder="Username"
        value={newUser.username}
        onChange={(e) => setNewUser({ ...newUser, username: e.target.value })}
      />
      <input
        type="email"
        placeholder="Email"
        value={newUser.email}
        onChange={(e) => setNewUser({ ...newUser, email: e.target.value })}
      />
      <button onClick={addUser}>Add User</button>
    </div>
  );
}

export default UserComponent;

