<template>
    <div class="signupform">
        <label for="username"> Username: </label> 
        <input type="text" id="username" name="username" v-model="username"><br><br>

        <label for="email"> Email: </label>
        <input type="text" id="email" name="email" v-model="email"><br><br>
        
        <label for="password"> Password: </label>
        <input type="password" id="password" name="password" v-model="password"><br><br>

        <button v-on:click="asyncsignup()" class="btn btn-primary"> Sign up </button> <br/><br/>
        <button v-on:click="redirectToLogin()" class="btn btn-secondary">Log in instead</button>
    </div>
</template>

<script>
import FeedAppDataService from "@/services/FeedAppDataService";
import { useStore } from "@/stores/store.js";
//import { Buffer } from 'node:buffer';
import axios from 'axios';

export default {
    name: "SingupForm",
    setup() {
        const store = useStore();
        console.log(store)
    },
    data(){
        return {
            username: '',
            email: '',
            password: ''
        } 
    },
    methods: {
        redirectToLogin() {
            this.$router.push({path:"/login"})
        },
        async asyncsignup() {
            const url = 'http://localhost:8080/agents/byUsername';
            //const token = Buffer.from(`${username}:${password}`, 'utf8').toString('base64');
            const data = {
                username:this.username,
                email:this.email,
                password:this.password
                };
            //const authHeader = { headers: {'Authorization': `Basic ${token}`} };

            let response = await axios.post(url, data);
            
            console.warn(response)
            if (response.code == 200) {
                alert("sign up successful")
                store.setItem("userinfo", JSON.stringify(response.data))
                this.$router.push("/login")
            }
        },
        testStore() {
            const store = useStore();
            store.$patch({
                username:this.username,
                email:this.email,
                password:this.password
                })

            console.log("Registered user with these credentials:", {
                username:this.username,
                email:this.email,
                password:this.password
                })
        }
    },
    mounted() {
    }
}
</script>

<style>
input {
    margin-left: 10px;
}
</style>
