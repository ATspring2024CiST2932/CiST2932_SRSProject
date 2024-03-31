// FrontEnd/src/main.jsx
import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App.jsx';
import './index.css';
import MenuList from "./components/MenuList.jsx";
import 'antd/dist/antd.css';

const rootElement = document.getElementById('root');
const root = createRoot(rootElement);
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// Use the MenuList component instead of a standalone Menu
<MenuList darkTheme={false} />;
