// gst validation
const gstPattern = /^\d{2}[A-Z]{5}\d{4}[A-Z]{1}[A-Z\d]{1}Z[A-Z\d]{1}$/;
const gstInput = document.getElementById('gst').value;

if (gstPattern.test(gstInput)) {
  console.log('Valid GST number');
} else {
  console.log('Invalid GST number');
}


// email validation
const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const emailInput = document.getElementById('email').value;

if (emailPattern.test(emailInput)) {
  console.log('Valid email address');
} else {
  console.log('Invalid email address');
}

