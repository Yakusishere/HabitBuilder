import {createRouter,createWebHistory} from 'vue-router'
//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import PlanPageVue from '@/views/plan/PlanPage.vue'
import AIVue from '@/views/plan/AI.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserPostVue from '@/views/user/UserPost.vue'
import UserResetInfoVue from '@/views/user/UserResetInfo.vue'
import CommunityPageVue from '@/views/community/CommunityPage.vue'
//定义路由关系
const routes=[
    {path:'/login',component:LoginVue},
    {
        path:'/',component:LayoutVue,
        children:[
            {path:'/plan/planpage',component:PlanPageVue},
            {path:'/plan/ai',component:AIVue},
            {path:'/user/userinfo',component:UserInfoVue},
            {path:'/user/userpost',component:UserPostVue},
            {path:'/user/userresetinfo',component:UserResetInfoVue},
            {path:'/community/communitypage',component:CommunityPageVue}
        ]
    },
]
//创建路由器
const router =createRouter({
    history:createWebHistory(),
    routes:routes
})
//导出路由
export default router