import { createRouter, createWebHistory } from "vue-router";
//import HomeView from "@/views/HomeView.vue";
import VotingView from "@/views/VotingView.vue";
import AdminView from  "@/views/AdminView.vue";
import ThankyouView from "@/views/ThankyouView.vue";
import MypollsView from "@/views/MypollsView.vue";
import AltLoginView from "@/views/AltLoginView.vue";
import CreatepollsView from "@/views/CreatepollsView.vue";
import SignupView from "@/views/SignupView.vue";
import Poll from "@/components/Poll.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: AltLoginView,
    },
    {
      path: "/admin",
      name: "admin",
      component: AdminView,
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
      component: AltLoginView,
    },
    {
      path: "/createpolls",
      name: "createpolls",
      component: CreatepollsView,
    },
    {
      path: "/signup",
      name: "signup",
      component: SignupView,
    },
    { 
      path: '/voting/:pollID', 
      component: Poll 
    },
  ],
});

export default router;
