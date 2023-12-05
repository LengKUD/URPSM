package com.lengch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lengch.entity.Menu;
import com.lengch.mapper.MenuMapper;
import com.lengch.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lengchenghao
 * @since 2023-11-24
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    MenuMapper menuMapper;
    @Override
    public List<Menu> getAllMenuTerr() {
//        List<Menu> menuList;
//        QueryWrapper queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("pid",0);
//        menuList = list(queryWrapper);
//        if (menuList == null) {
//            return null;
//        }
//        for (Menu m:
//                menuList) {
//            this.getMenuChildren(m);
//        }
//        return menuList;

        //一次性取出所有数据
//        List<Menu> allList = list();
        List<Menu> allList = menuMapper.getAllMenuList();
        if (allList == null) {
            return null;
        }

        return setMenuToTree(allList);

    }

    /**
     * 将具有父子关系的平级列表，转换为树形列表
     * @param allList
     * @return
     */
    @Override
    public List<Menu> setMenuToTree(List<Menu> allList){
        //组织菜单树形结构。先找一级菜单,pid==0 的为一级菜单
        List<Menu> menuList = allList.stream().filter(menu -> menu.getPid()==0).collect(Collectors.toList());
        this.setMenuChildren(menuList,allList);
        return menuList;
    }
    /**
     * 将找到父节点的子节点并设置到父节点中
     * @param parentList 要设置子节点的集合
     * @param allList 总结
     */
    public void setMenuChildren(List<Menu> parentList, List<Menu> allList) {

        for (Menu p: parentList) {
//            找到p节点下所有子节点
            List<Menu> children = allList.stream().filter(menu -> p.getId().equals(menu.getPid())).collect(Collectors.toList());
            p.setChildren(children);
//            将子节点进行递归寻找
            setMenuChildren(children,allList);
        }

    }

    @Override
    public void removeEmptyChildren(List<Menu> children,List<String> list) {
        List<Menu> temp = new ArrayList<>();
        for (Menu c: children) {
//            如果是叶子节点
            if (c.getChildren() == null || c.getChildren().size()==0) {
//                判断是否在列表中，不在列表中，加入到移除temp中
                if (!list.contains(c.getId().toString())) {
                    temp.add(c);
                }
            }else{
//                如果不是叶子节点，则将子节点递归处理
                removeEmptyChildren(c.getChildren(),list);
//                处理完子节点后，要再次判断自身是否成为叶子节点（子节点完全移除的情况）
//                如果成为叶子节点也要将自己放入待移除temp中
                if (c.getChildren() == null || c.getChildren().size()==0) {
//                    原来有子节点，全部移除后就默认将其删除
                    temp.add(c);
//                    原来有子节点，全部移除后就还要在判断一下是否包含在列表中，也有这种可能
//                    if (!list.contains(c.getId().toString())) {
//                        temp.add(c);
//                    }
                }
            }
        }
        children.removeAll(temp);

    }


    @Override
    public Boolean delMenuById(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        List<Menu> ChildrenList = list(queryWrapper);
        if (ChildrenList == null || ChildrenList.size()==0) {
            return removeById(id);
        }
        for (Menu m:
                ChildrenList) {
            delMenuById(m.getId());
        }
        return removeById(id);

    }

    public void getMenuChildren(Menu menu){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",menu.getId());
        List<Menu> ChildrenList = list(queryWrapper);
        if (ChildrenList == null || ChildrenList.size()==0) {
            return;
        }
        menu.setChildren(new ArrayList<>());
        for (Menu m:
                ChildrenList) {
            menu.getChildren().add(m);
            getMenuChildren(m);
        }


    }

}
