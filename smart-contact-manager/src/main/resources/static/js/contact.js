console.log("Contacts.js");
// const baseURL = "http://localhost:8081";
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

// delete contact
async function deleteContact(id) {

  Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, delete it!"
  }).then((result) => {
    if (result.isConfirmed) {
      fetch(`http://localhost:8082/user/contacts/delete/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then(response => response.json())
        .then(data => {
          console.log(data);
          window.location.reload();
        })
        .catch(error => {
          console.error('Error:', error);
        });
      Swal.fire(
        'Deleted!',
        'Your contact has been deleted.',
        'success'
      )
      window.location.reload();
    }
    else {
      Swal.fire("Cancelled", "Your contact is safe :)", "error");
    }
  });
}



