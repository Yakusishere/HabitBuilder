<script lang=ts setup>
import {
  Calendar,
  Menu as IconMenu,
  User,
  UserFilled
} from '@element-plus/icons-vue'
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>

<template>
   <div class="common-layout">
    <el-container class="layout-countainer">
      <el-aside width="200px" class="aside">
        <!--接下来为左侧-->
        <el-row class="tac">
    <el-col :span="24">
      <div class="avatar-text-container">
      <el-avatar :size="50" icon="UserFilled" />
      <el-text class="grades" >积分</el-text>
      </div>
      <el-text class="username">用户Yuki</el-text>
      <el-text class="text">，你好!</el-text>
      <el-menu
        default-active="/plan/planpage"
        class="el-menu"
        @open="handleOpen"
        @close="handleClose"
      >
        <el-sub-menu index="1">
          <template #title>
            <el-icon><Calendar /></el-icon>
            <span>计划</span>
          </template>
            <el-menu-item index="/plan/planpage">日程</el-menu-item>
            <el-menu-item index="/plan/ai">AI</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/community/communitypage">
          <el-icon><icon-menu /></el-icon>
          <span>社区</span>
        </el-menu-item>
        <el-sub-menu index="3">
          <template #title>
            <el-icon><User /></el-icon>
            <span>我的</span>
          </template>
            <el-menu-item index="/user/userinfo">个人资料</el-menu-item>
            <el-menu-item index="/user/userresetinfo">账号设置</el-menu-item>
            <el-menu-item index="/user/userpost">个人帖子</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-col>
  </el-row>
      </el-aside>
      <el-main>
        <router-view></router-view>
        <!--主区域-->
      </el-main>
    </el-container>
  </div>

</template>

<style lang="scss" scoped>
 .aside {
    background-color: white;
    height:100vh
  } 
  
.layout-container {
    height: 100%; 
    .el-menu {
            border-right: none;
        }
    
  }

.avatar-text-container {
  margin: 20px; /* 调整这个值来设置头像的位置 */
  align-items: center; /* 头像和文字垂直居中对齐 */
  //position: fixed;
  //top: 20px; /* 调整这个值以设置垂直位置 */
  //left: 20px; /* 调整这个值以设置水平位置 */
  /* 你也可以使用 bottom 和 left 来定位 */
}
.grades{
  //display: flex;
  //align-items: flex-start; /* 左对齐 */
  //flex-direction: column; /* 垂直排列 */
  //line-height: 50px;
  margin-left: 20px; /* 调整这个值以设置头像和文字之间的间距 */
  //white-space: pre-line; /* 保持换行符 */
  font-size: 24px;
  color: #ff0000; /* 设置积分文字的颜色，例如红色 */

}
.username{
  margin-left: 30px;
}
</style>
  