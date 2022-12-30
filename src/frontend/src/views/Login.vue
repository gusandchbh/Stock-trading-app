<template>
  <div>
  <div class="quote">
    <h1> '{{this.quote}}' <br> - Ye</h1>
  </div>
  <form @submit.prevent="login">
    <label for="username">Username:</label>
    <input id="username" v-model="username" />
    <br />
    <label for="password">Password:</label>
    <input id="password" v-model="password" type="password" />
    <br />
    <button type="submit">Log In</button>
  </form>
  </div>
</template>
<script>
import axios from "axios";
export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
      quote: '',
    }
  },
  created() {
    this.getQuote()
  },
  methods: {
    async login() {
      try {
        const response = await axios.get('https://api.kanye.rest/')
        console.log(response)
        // login successful, store user data or set cookie
      } catch (error) {
        console.log(error)
      }
    }, async getQuote() {
      try {
        const response = await axios.get('https://api.kanye.rest/')
        this.quote = response.data.quote
      } catch (error){
        console.log(error)
      }
    }
  }
}

</script>