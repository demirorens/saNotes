import './App.css';
import 'antd/dist/antd.css';
import { Layout } from 'antd';
import MenuSA from './menu/MenuSA';


const { Header, Content, Footer, Sider } = Layout;

function App() {
  return (
    <div className="App">
      <Layout>
        <Sider
          breakpoint="lg"
          collapsedWidth="0"
          onBreakpoint={broken => {
            console.log(broken);
          }}
          onCollapse={(collapsed, type) => {
            console.log(collapsed, type);
          }}
        >
          <div className="logo" />
          <MenuSA/>          
        </Sider>
        <Layout>
          <Header className="site-layout-sub-header-background" style={{ padding: 0,color:"white" }}> Header</Header>
          <Content style={{ margin: '24px 16px 0' }} >
            <div className="site-layout-background" style={{ padding: 24, minHeight: 700 }}>
              content
            </div>
          </Content>
          <Footer style={{ textAlign: 'center' }}>Footer</Footer>
        </Layout>
      </Layout>
    </div>
  );
}

export default App;
