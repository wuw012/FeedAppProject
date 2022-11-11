import { createRouter, createWebHistory } from "vue-router";
//import HomeView from "@/views/HomeView.vue";
import VotingView from "@/views/VotingView.vue";
import AboutView from  "@/views/AboutView.vue";
import ThankyouView from "@/views/ThankyouView.vue";
import MypollsView from "@/views/MypollsView.vue";
import AltLoginView from "@/views/AltLoginView.vue";
import CreatepollsView from "@/views/CreatepollsView.vue";
import SignupView from "@/views/SignupView.vue";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
  /*
     {
      path: "/",
      name: "home",
      component: HomeView,
    },
     {
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
    }
  ],
});

export default router;
