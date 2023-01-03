<template>
  <nav>
    <RouterLink to="/">Register</RouterLink>
    <RouterLink to="/login">Login</RouterLink>
    <RouterLink to="/userpage">Test</RouterLink>
  </nav>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Navbar",
};
</script>

<style scoped>
nav {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background-color: lightgray;
}
nav ul {
  list-style: none;
  display: flex;
  justify-content: space-between;
}
nav li {
  display: inline-block;
}
nav a {
  color: black;
  text-decoration: none;
  padding: 0.5em 1em;
}
</style>
