<template>
    <br/>
    <div class="wrap">
        <form v-on:submit.prevent="onSubmit">
            <input type="text" id="username" name="username" placeholder="Username" v-model="username"><br><br>

            <input type="text" id="email" name="email" placeholder="Email" v-model="email"><br><br>
            
            <input type="password" id="password1" name="password1" placeholder="Password" v-model="password1"><br><br>

            <input type="password" id="password2" name="password2" placeholder="Repeat password" v-model="password2"><br><br>
            <p> {{ error }} </p>
        </form>

        <button v-on:click="signup()" class="btn btn-primary mr-2" type="submit"> Sign up </button> 
        <button v-on:click="redirectToLogin()" class="btn btn-secondary">Log in instead</button>
    </div>

    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
</template>

<script>
import FeedAppDataService from "@/services/FeedAppDataService";
import axios from 'axios';

export default {
    name: "SingupForm",
    data(){
        return {
            username: '',
            email: '',
            password1: '',
            password2: '',
            error: '',
            userExists: false,
            createdUser: false,
        } 
    },
    methods: {
        redirectToLogin() {
            this.$router.push({path:"/login"})
        },
        async checkAvailableUsername() {
            await FeedAppDataService.exists(this.username).then((userExists) => this.userExists=userExists);
        },
        async postUser() {
            await FeedAppDataService.postUser(this.username, this.email, this.password1).then((status) => {
                if (status == 200) {
                    this.createdUser = true;
                }
            })
        },
        signup() {
            this.checkAvailableUsername().then(() => {
                if(!this.userExists && this.email && this.password1) {
                    this.postUser(this.username, this.email, this.password1).then(()=> {
                        if (this.createdUser) {
                            alert("User created")
                            this.$router.push({path:"/login"})
                        } else {
                            alert("Something went wrong")
                        }
                    });  
                } else {
                    this.error = "Username exists and/or fill in all fields"
                }
            })
        } 
    },
    mounted() {
    }
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
