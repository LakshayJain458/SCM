async function deleteContact(id) {
  const result = await Swal.fire({
      title: "Are you sure?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!",
  });

  if (result.isConfirmed) {
      try {
          // Use fetch API for proper DELETE request
          const response = await fetch(`/user/contact/delete/${id}`, {
              method: 'DELETE',
              headers: {
                  'Content-Type': 'application/json',
                  'X-Requested-With': 'XMLHttpRequest' // Helps identify AJAX requests
              }
          });

          if (response.ok) {
              // Success notification
              await Swal.fire(
                  'Deleted!',
                  'Contact has been deleted.',
                  'success'
              );
              // Refresh the page after success
              window.location.reload();
          } else {
              // Server error handling
              const errorData = await response.json();
              Swal.fire(
                  'Error!',
                  errorData.message || 'Failed to delete contact',
                  'error'
              );
          }
      } catch (error) {
          // Network error handling
          console.error('Delete error:', error);
          Swal.fire(
              'Error!',
              'Could not connect to the server',
              'error'
          );
      }
  }
}