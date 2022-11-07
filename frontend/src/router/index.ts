import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import VotingView from "@/views/VotingView.vue";
import AboutView from  "@/views/AboutView.vue";
import ThankyouView from "@/views/ThankyouView.vue";
import MypollsView from "@/views/MypollsView.vue";
import LoginView from "@/views/LoginView.vue";
import CreatepollsView from "@/views/CreatepollsView.vue";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
     {
      path: "/",
      name: "home",
      component: HomeView,
    },
/*     {
      path: "/login",
      name: "login",
      component: Login,
    }, */
    {
      path: "/about",
      name: "about",
      component: AboutView,
    },
    {
      path: "/voting",
      name: "voting",
      component: VotingView,
    },
    {
      path: "/thankyou",
      name: "thankyou",
      component: ThankyouView,
    },
    {
      path: "/mypolls",
      name: "mypolls",
      component: MypollsView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/createpolls",
      name: "createpolls",
      component: CreatepollsView,
    }
  ],
});

export default router;
