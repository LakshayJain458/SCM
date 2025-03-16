console.log("Script loaded");

// Get the current theme from localStorage
let currentTheme = getTheme();

// Initialize theme on page load
document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});

// Main function to set up theme toggling
function changeTheme() {
  // Apply the initial theme
  changePageTheme(currentTheme, "");

  // Set event listener for the button
  const changeThemeButton = document.querySelector("#theme_change_button");

  changeThemeButton.addEventListener("click", () => {
    let oldTheme = currentTheme;
    console.log("Theme change button clicked");

    // Toggle between dark and light themes
    currentTheme = currentTheme === "dark" ? "light" : "dark";
    
    console.log(currentTheme);
    changePageTheme(currentTheme, oldTheme);
  });
}

// Store the selected theme in localStorage
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

// Retrieve the stored theme or default to "light"
function getTheme() {
  return localStorage.getItem("theme") || "light";
}

// Change the current page theme
function changePageTheme(theme, oldTheme) {
  // Update theme in localStorage
  setTheme(theme);

  // Remove the old theme class if it exists
  if (oldTheme) {
    document.documentElement.classList.remove(oldTheme);
  }

  // Apply the new theme class
  document.documentElement.classList.add(theme);

  // Update the theme icon
  document.querySelector("#theme_icon").src = theme === "dark" ? "/images/light.png" : "/images/night-mode.png";
}
