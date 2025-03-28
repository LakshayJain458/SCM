document.addEventListener('DOMContentLoaded', function() {
  const sidebar = document.getElementById('logo-sidebar');
  const toggleContainer = document.getElementById('toggle-container');
  const toggle = document.getElementById('sidebar-toggle');
  const close = document.getElementById('close-sidebar');
  const overlay = document.getElementById('sidebar-overlay');

  function toggleSidebar() {
    const isOpen = !sidebar.classList.contains('-translate-x-full');
    sidebar.classList.toggle('-translate-x-full');
    overlay.classList.toggle('hidden');
    
    // Toggle button visibility
    toggleContainer.classList.toggle('opacity-0');
    toggleContainer.classList.toggle('pointer-events-none');
  }

  toggle.addEventListener('click', toggleSidebar);
  close.addEventListener('click', toggleSidebar);
  overlay.addEventListener('click', toggleSidebar);
});