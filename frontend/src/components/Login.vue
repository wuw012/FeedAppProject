<template>
    <br/>
    <div class="wrap">
        <form v-on:submit.prevent="onSubmit" class="form-horizontal">
            <input type="text" id="username" name="username" placeholder="Username" v-model="username"><br><br>

            <input type="password" id="password" name="password" placeholder="Password" v-model="password"><br><br>

            <p> {{ error }} </p>

            <button class="btn btn-primary mr-2" v-on:click="login()" type="submit"> Log in </button>
            <button class="btn btn-secondary" v-on:click="redirectToSignup()"> Sign up instead</button>
        </form>
    </div>
</template>

<script>
import FeedAppDataService from "@/services/FeedAppDataService";
import axios from 'axios';
export default {
    name: "LogInForm",
    data(){
        return {
            username: '',
            password: '',
            isAuthenticated: false,
            isAdmin: false,
            error: ""
        } 
    },
    methods: {
        redirectToSignup() {
            this.$router.push({ path: '/signup' });
        },
        async updateAuthStatus() {
            const hashedPwd = FeedAppDataService.hashPwd(this.password);
            await FeedAppDataService.isAuthenticated(this.username, hashedPwd).then((userAuthenticated) => this.isAuthenticated=userAuthenticated);
            await FeedAppDataService.isAdmin(this.username).then((adminUser) => this.isAdmin = adminUser);
        },
        login() {
            this.updateAuthStatus().then(() => {
                if(this.isAuthenticated){
                    const hashedPwd = FeedAppDataService.hashPwd(this.password);
                    localStorage.setItem("username", this.username);
                    localStorage.setItem("password", hashedPwd);
                    if(this.isAdmin) {
                        this.$router.push({ path: '/admin' })
                        document.location.href = "/admin";
                    }else{
                        this.$router.push({path:"/mypolls"})
                        document.location.href = "/mypolls";
                    }
                } else {
                    this.error = "Could not authenticate user"
                }
            })
        } 
    },  
}
</script>

<style scoped>
.wrap {
  background-color: #ffffff;
  padding: 2%;
  width: 25%;
  min-width:350px;
  margin: 0 auto;
  -moz-border-radius: 6px;
  -webkit-border-radius: 6px;
  box-shadow: 0 0 5px #ccc;
  border: 1px solid #fff;
}

input {
  width: 90%;
  margin-bottom: 10px;
  padding: 5%;
  -moz-border-radius: 6px;
  -webkit-border-radius: 6px;
  border: 1px solid #efefef;
  font-size: 15px;
  -webkit-transition: all .2s ease-in-out;
  -moz-transition: all .2s ease-in-out;
  transition: all .2s ease-in-out;
}

input:focus {
  outline: none;
  border-color: #9ecaed;
  box-shadow: 0 0 10px #9ecaed;
  -webkit-transition: all .2s ease-in-out;
  -moz-transition: all .2s ease-in-out;
  transition: all .2s ease-in-out;
}

input.submit {
  width: 100%;
  padding: 5%;
  -moz-border-radius: 6px;
  -webkit-border-radius: 6px;
  border: 1px solid #45b3e7;
  font-size: 15px;
  background-color: #45b3e7;
  color: #fff;
  margin-top: 25px;
  -webkit-transition: all .2s ease-in-out;
  -moz-transition: all .2s ease-in-out;
  transition: all .2s ease-in-out;
}

input.submit:hover {
  width: 100%;
  padding: 5%;
  -moz-border-radius: 6px;
  -webkit-border-radius: 6px;
  border: 1px solid #32CD32;
  font-size: 15px;
  background-color: #32CD32;
  color: #fff;
  margin-top: 25px;
  -webkit-transition: all .2s ease-in-out;
  -moz-transition: all .2s ease-in-out;
  transition: all .2s ease-in-out;
}
</style>
