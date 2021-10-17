import { Button, Dropdown, Menu,message } from 'antd';
import Notebooks from "./Notebooks";
import Tags from "./Tags";
import {
    BookOutlined,
    TagsOutlined,
    DownOutlined,
    UserOutlined
} from '@ant-design/icons';


const menu = (
    <Menu onClick={handleMenuClick}>
      <Menu.Item key="1" icon={<UserOutlined />}>
        1st menu item
      </Menu.Item>
      <Menu.Item key="2" icon={<UserOutlined />}>
        2nd menu item
      </Menu.Item>
      <Menu.Item key="3" icon={<UserOutlined />}>
        3rd menu item
      </Menu.Item>
    </Menu>
  );

  function handleMenuClick(e:any) {
    message.info('Click on menu item.');
    console.log('click', e);
  }


function MenuSA(){
    return(
        <div className="MenuSA">
            <Menu
                mode="inline"
                theme="dark"
            >
                
                <Dropdown overlay={menu} trigger={['click']}>
                    <Button type="primary" shape="round" style={{ background: "green", borderColor: "white"}} block>
                        + New <DownOutlined style={{ position:"absolute", right:"5px" }}/>
                    </Button>
                </Dropdown>
                <Menu.SubMenu key="notebooks" icon={<BookOutlined />} title="Note Books">
                    <Menu.Item key="4">Book1</Menu.Item>
                    <Menu.Item key="5">Book2</Menu.Item>
                    <Menu.Item key="6">Book3</Menu.Item>
                </Menu.SubMenu> 
                <Menu.SubMenu key="tags" icon={<TagsOutlined />} title="Tags">
                    <Menu.Item key="7">Book1</Menu.Item>
                    <Menu.Item key="8">Book2</Menu.Item>
                    <Menu.Item key="9">Book3</Menu.Item>
                </Menu.SubMenu> 
                <Menu.Item key="10">Arşiv</Menu.Item> 
                               
            </Menu>
        </div>
    )
}
export default MenuSA;