import React from 'react';
import {Menu} from 'antd';
import { ProfileOutlined, UserOutlined, SettingOutlined } from '@ant-design/icons';





const MenuList = ({ darkTheme }) => {
    return (
        <Menu theme={darkTheme ? 'dark' : 'light'} mode="inline" className="menu-bar">
             <Menu.Item key="tasks" icon={<ProfileOutlined />}>
        <a href="/tasks.html" target="" rel="noopener noreferrer">
          Tasks
        </a>
        </Menu.Item>
        <Menu.Item key="Employees" icon={<ProfileOutlined />}>
        <a href="/index.html" target="" rel="noopener noreferrer">
          Employees
        </a>
      </Menu.Item>
      <Menu.Item key="Login" icon={<SettingOutlined />}>
        <a href="/login.html" target="_blank" rel="noopener noreferrer">
          Login
        </a>
      </Menu.Item>
    </Menu>
  );
}

export default MenuList