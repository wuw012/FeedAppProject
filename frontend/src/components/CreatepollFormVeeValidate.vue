<script >
/*
<template>
  <VeeForm v-slot="{ handleSubmit }" :validation-schema="schema" as="div">
    <form @submit="handleSubmit($event, onSubmit)">
      <Field name="email" type="email" />
      <ErrorMessage name="email" />
      <Field name="password" type="password" />
      <ErrorMessage name="password" />
      <button>Submit</button>
    </form>
  </VeeForm>
</template>

import { Form as VeeForm, Field } from 'vee-validate';
import * as yup from 'yup';
const schema = yup.object({
  email: yup.string().required().email(),
  password: yup.string().required().min(8),
});
function onSubmit(values) {
  // Submit values to API...
  alert(JSON.stringify(values, null, 2));
}
*/
</script>