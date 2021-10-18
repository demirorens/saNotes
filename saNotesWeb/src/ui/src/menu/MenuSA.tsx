import React from 'react';
import { RestClient } from '../client/RestClient';
import { Button, Dropdown, Menu,message } from 'antd';
import Notebooks from "./Notebooks";
import Tags from "./Tags";
import {
    BookOutlined,
    TagsOutlined,
    DownOutlined,
    UserOutlined,
    TagOutlined,
    HddOutlined,
    FileAddOutlined,
    CheckOutlined
} from '@ant-design/icons';




const menu = (
    <Menu onClick={handleMenuClick}>
        <Menu.Item key="newNote" icon={<FileAddOutlined />}>
            Note
        </Menu.Item>
        <Menu.Item key="newToDo" icon={<CheckOutlined />}>
            TODO
        </Menu.Item>
    </Menu>
);

function handleMenuClick(e:any) {
    message.info('Click on menu item: '+ e.key);
    console.log('click', e);
}



function MenuSA(){

    let [notebooks, setNoteBooks] = React.useState<Array<any>>([])
    let [tags, setTags] = React.useState<Array<any>>([])
       
    React.useEffect(() => {
        RestClient.getNoteBooks()
                    .then(notebooks => setNoteBooks(notebooks))
        RestClient.getTags()
                    .then(tags => setTags(tags))
    }, [])

    return(
        <div className="MenuSA">
            <Menu onClick={handleMenuClick}
                mode="inline"
                theme="dark"
            >
                
                <Dropdown overlay={menu} trigger={['click']}>
                    <Button type="primary" shape="round" style={{ background: "green", borderColor: "white"}} block>
                        + New <DownOutlined style={{ position:"absolute", right:"5px" }}/>
                    </Button>
                </Dropdown>
                <Menu.SubMenu key="notebooks" icon={<BookOutlined />} title="Note Books">
                    {notebooks.map((notebook: any, i: number) =>
                        <Menu.Item key={"noteBook_"+notebook.id} icon={<BookOutlined />}>{notebook.name}</Menu.Item>
                    )}
                    <Menu.Item key="newNoteBook" icon={<BookOutlined />} style={{color:'green'}}><span>+ New Note Book</span></Menu.Item>
                </Menu.SubMenu> 
                <Menu.SubMenu key="tags" icon={<TagsOutlined />} title="Tags">
                    {tags.map((tag: any, i: number) =>
                        <Menu.Item key={"tag_"+tag.id} icon={<TagOutlined />}>{tag.name}</Menu.Item>
                    )}
                    <Menu.Item key="newTag" icon={<TagOutlined />} style={{color:'green'}}><span>+ New Tag</span></Menu.Item>
                </Menu.SubMenu> 
                <Menu.Item key="Archive" icon={<HddOutlined />}>Archive</Menu.Item> 
                               
            </Menu>
        </div>
    )
}
export default MenuSA;