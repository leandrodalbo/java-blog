// Runs synchronously, before first paint, so there's no flash of the wrong
// theme. Falls back to system preference until the reader picks explicitly,
// then remembers that choice.
(function () {
  var stored = localStorage.getItem("theme");
  var prefersDark = window.matchMedia("(prefers-color-scheme: dark)").matches;
  var dark = stored ? stored === "dark" : prefersDark;
  document.documentElement.classList.toggle("dark", dark);
})();

function toggleTheme() {
  var dark = document.documentElement.classList.toggle("dark");
  localStorage.setItem("theme", dark ? "dark" : "light");
}
