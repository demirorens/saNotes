import { Menu } from 'antd';
import {
    TagsOutlined 
} from '@ant-design/icons';


function Tags(){
    return(
        <div>            
            <Menu.SubMenu key="sub2" icon={<TagsOutlined  />} title="Tags">
                <Menu.Item key="7">Tag1</Menu.Item>
                <Menu.Item key="8">Tag2</Menu.Item>
                <Menu.Item key="9">Tag3</Menu.Item>
            </Menu.SubMenu>                
        </div>
    )
}
export default Tags;