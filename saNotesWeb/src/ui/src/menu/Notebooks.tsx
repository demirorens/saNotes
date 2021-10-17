import { Menu } from 'antd';
import {
    BookOutlined
} from '@ant-design/icons';


function Notebooks(){
    return(
        <div>            
            <Menu.SubMenu key="sub1" icon={<BookOutlined />} title="Note Books">
                <Menu.Item key="4">Book1</Menu.Item>
                <Menu.Item key="5">Book2</Menu.Item>
                <Menu.Item key="6">Book3</Menu.Item>
            </Menu.SubMenu>                
        </div>
    )
}
export default Notebooks;