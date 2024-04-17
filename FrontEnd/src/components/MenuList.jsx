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
        </Menu.Item>
            <Menu.Item key="Login" icon={<SettingOutlined />}>
            <a href="/login.html" target="" rel="noopener noreferrer">
              Login
              </a>
              </Menu.Item>
        </Menu>
    )
    
}

export default MenuList;
