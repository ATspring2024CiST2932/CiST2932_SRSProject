// FrontEnd/src/main.jsx
import React from 'react';
import { createRoot } from 'react-dom/client'; // Import createRoot from react-dom/client
import App from './App.jsx';
import './index.css';

const rootElement = document.getElementById('root'); // Get the root element
const root = createRoot(rootElement); // Create a root
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
