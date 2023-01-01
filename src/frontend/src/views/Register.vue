<template>
  <q-layout>
    <q-page-container>
  <q-page>
    <q-card>
      <q-card-section>
        <h1>Create an Account</h1>
        <form>
          <q-input v-model="username" label="Username" type="text" />
          <q-input v-model="password" label="Password" type="password" />
          <q-input v-model="firstName" label="First Name" type="text" />
          <q-input v-model="lastName" label="Last Name" type="text" />
          <q-input v-model="birthDate" label="Birth Date" type="date" />
          <q-radio v-model="gender" label="Gender" :options="genderOptions" />
          <q-btn label="Create Account" @click.native="createAccount" />
        </form>
      </q-card-section>
    </q-card>
  </q-page>
    </q-page-container>
  </q-layout>
</template>

<script>
export default {
  name: 'RegisterPage',
  data() {
    return {
      username: '',
      password: '',
      firstName: '',
      lastName: '',
      birthDate: '',
      gender: '',
      genderOptions: [
        { label: 'Male', value: 'male' },
        { label: 'Female', value: 'female' },
        { label: 'Other', value: 'other' },
      ],
    }
  },
  methods: {
    createAccount() {
      // Your code to create the account goes here
      // You can access the form data using `this.username`, `this.password`, etc.
    },
  },
}
</script>
