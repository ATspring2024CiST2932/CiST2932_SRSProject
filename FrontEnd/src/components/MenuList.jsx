import React from 'react';
import {Menu} from 'antd';
import { ProfileOutlined, UserOutlined, SettingOutlined } from '@ant-design/icons';





const MenuList = () => {
    return (
        <Menu theme= "dark" mode="inline" className="menu-bar">
             <Menu.Item key="tasks" icon={<ProfileOutlined />}>
        <a href="/tasks.html" target="" rel="noopener noreferrer">
          Tasks
        </a>
        </Menu.Item>
        <Menu.Item key="Employees" icon={<UserOutlined />}>
        <a href="/index.html" target="" rel="noopener noreferrer">
          Employees
        </a>
        </Menu.Item>
            <Menu.Item key="Login" icon={<SettingOutlined />}>
            <a href="/login.html" target="" rel="noopener noreferrer">
              Login
              </a>
              </Menu.Item>
        </Menu>
    )
    
}

export default MenuList