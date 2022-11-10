<template>
    <form id="signup-form" v-on:submit.prevent="submit">
        <div class="row">
            <div class="col-12 form-group">
                <label class="col-form-label col-form-label-lg">Full Name <span class="text-danger">*</span></label>
                <input type="text" v-model.trim="fullname"  class="form-control form-control-lg">
            </div>
            <div class="col-12 form-group">
                <label class="col-form-label col-form-label-lg">Email <span class="text-danger">*</span></label>
                <input type="email" v-model.trim="email" class="form-control form-control-lg">
            </div>
            <div class="col-12 form-group">
                <label class="col-form-label col-form-label-lg">Password <span class="text-danger">*</span></label>
                <input type="password" v-model.trim="password" class="form-control form-control-lg">
            </div>
            <div class="col-12 form-group text-center">
                <button class="btn btn-vue btn-lg col-4">Sign Up</button>
            </div>
        </div>
    </form>
</template>

<script>
export default {
    name: 'SignupForm',
    data: function() {
        return {
            fullname: '', 
            email: '', 
            password: '',
        }
    }
}
</script>