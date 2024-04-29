import { Layout, theme } from 'antd'
import Logo from './components/Logo';
import MenuList from './components/MenuList';



const { Header, Sider } = Layout;
function App() {
  

  const {
    token: {colorBgContainer},
    
  } = theme.useToken();
  return (
    <>
      <Layout>
        <Sider className="sidebar">
          <Logo />
          <MenuList />       
        </Sider>
        <Layout>
          <Header style={{padding: 0, background: colorBgContainer}}>
          </Header>
        </Layout>
      </Layout>      
    </>
  )
}

export default App

