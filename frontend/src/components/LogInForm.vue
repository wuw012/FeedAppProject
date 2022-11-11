<template>
    <div class="loginform">
        <p>please fill out form beneath to create a new poll</p>

        <form @submit="checkForm" class="form-horizontal" action="/postPoll" method="POST">

            <label for="username"> Username: </label> 
            <input type="text" id="username" name="username" v-model="username"><br><br>
            <p> username {{username}} </p>

            <label for="email"> Email: </label>
            <input type="text" id="email" name="email" v-model="email"><br><br>
            <p> email {{email}} </p>

            <label for="password"> Password: </label>
            <input type="password" id="password" name="password" v-model="password"><br><br>
            <p> password {{password}} </p>

            <input type="submit" value="Submit">

            <p v-if="errors?.length">
                <b>Please correct the following error(s):</b>
                <ul>
                <li v-for="error in errors">{{ error }}</li>
                </ul>
            </p>
        </form>

    </div>
</template>

<script>
const apiUrl = 'https://vuecookbook.netlify.app/.netlify/functions/product-name?name='

export default {
    name: "LogInForm",
    data() {
        return {
            form: {
                username: "",
                email: "",
                password: "",
                errors: [],
            }
        }
    }
}
</script>

<style>
input {
    margin-left: 10px;
}
</style>
