console.log("Contacts.js");

/**
const viewContactModal = document.getElementById("default-modal");

// options with default values
const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onHide: () => {
    console.log("modal is hidden");
  },
  onShow: () => {
    setTimeout(() => {
      contactModal.classList.add("scale-100");
    }, 50);
  },
  onToggle: () => {
    console.log("modal has been toggled");
  },
};

// instance options object
const instanceOptions = {
  id: "default-modal",
  override: true,
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
  contactModal.show();
}

// function closeContactModal() {
//   contactModal.hide();
// }

// function call to load user data
async function loadContactData(id) {
  console.log("id " + id);

  try {
    const data = await (await fetch(`http://localhost:8082/api/contacts/{id}`)).json();
    console.log(data)
    document.querySelector("#contact_name").innerHTML = data.name;
  } catch (error) {
    console.log(error)
  }

}
*/

// delete contact
async function deleteContact(id) {
  Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, delete it!",
  }).then((result) => {
    if (result.isConfirmed) {
      fetch(`http://localhost:8082/user/contacts/delete/${id}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          window.location.reload();
        })
        .catch((error) => {
          console.error("Error:", error);
        });
      Swal.fire("Deleted!", "Your contact has been deleted.", "success");
      window.location.reload();
    } else {
      Swal.fire("Cancelled", "Your contact is safe :)", "error");
    }
  });
}

// view contact details.
/**
 async function viewProfile(userId) {
            try {
                const response = await fetch(`/user/${userId}`);
                const user = await response.json();

                if (!user) {
                    Swal.fire({
                        icon: 'error',
                        title: 'User not found',
                        text: `No user found with ID ${userId}`,
                    });
                    return;
                }

                Swal.fire({
                    title: 'User Profile',
                    html: `
                        <div style="text-align: left;">
                            <img src="${user.img}" alt="Profile Picture" style="border-radius: 50%; margin-bottom: 10px;">
                            <p><strong>Name:</strong> ${user.name}</p>
                            <p><strong>Email:</strong> ${user.email}</p>
                            <p><strong>Location:</strong> ${user.location}</p>
                            <p><strong>Bio:</strong> ${user.bio}</p>
                        </div>
                    `,
                    showCloseButton: true,
                    focusConfirm: false,
                    confirmButtonText: 'Close',
                    customClass: {
                        popup: 'swal-wide'
                    }
                });
            } catch (error) {
                console.error('Error fetching user data:', error);
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'An error occurred while fetching user data. Please try again later.',
                });
            }
        }
 */
