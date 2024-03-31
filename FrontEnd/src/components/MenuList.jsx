import { ProfileOutlined, SettingOutlined } from '@ant-design/icons';
import { Menu } from 'antd';

const MenuList = ({ darkTheme }) => {
  const menuItems = [
    {
      key: 'tasks',
      icon: <ProfileOutlined />,
      label: (
        <a href="/tasks.html" target="" rel="noopener noreferrer">
          Tasks
        </a>
      ),
    },
    {
      key: 'Employees',
      icon: <ProfileOutlined />,
      label: (
        <a href="/index.html" target="" rel="noopener noreferrer">
          Employees
        </a>
      ),
    },
    {
      key: 'settings',
      icon: <SettingOutlined />,
      label: 'Settings',
    },
  ];

  return (
    <Menu
      theme={darkTheme ? 'dark' : 'light'}
      mode="inline"
      className="menu-bar"
      items={menuItems}
    />
  );
};

export default MenuList;
