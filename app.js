// SARA Application JavaScript

// Initialize Bootstrap tooltips and popovers
document.addEventListener('DOMContentLoaded', function () {
    // Initialize tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Initialize popovers
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });

    // Auto-hide alerts after 5 seconds
    setTimeout(function() {
        var alerts = document.querySelectorAll('.alert:not(.alert-permanent)');
        alerts.forEach(function(alert) {
            var bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);

    // Add loading state to form submissions
    document.querySelectorAll('form').forEach(function(form) {
        form.addEventListener('submit', function() {
            var submitBtn = form.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.classList.add('loading');
                submitBtn.disabled = true;
            }
        });
    });

    // Confirmation dialogs for delete actions
    document.querySelectorAll('[data-confirm]').forEach(function(element) {
        element.addEventListener('click', function(e) {
            var message = this.getAttribute('data-confirm');
            if (!confirm(message)) {
                e.preventDefault();
                return false;
            }
        });
    });

    // Auto-update timestamps
    updateTimestamps();
    setInterval(updateTimestamps, 60000); // Update every minute
});

// Function to update relative timestamps
function updateTimestamps() {
    document.querySelectorAll('[data-timestamp]').forEach(function(element) {
        var timestamp = new Date(element.getAttribute('data-timestamp'));
        element.textContent = formatRelativeTime(timestamp);
    });
}

// Format relative time (e.g., "2 hours ago")
function formatRelativeTime(date) {
    var now = new Date();
    var diff = now - date;
    var seconds = Math.floor(diff / 1000);
    var minutes = Math.floor(seconds / 60);
    var hours = Math.floor(minutes / 60);
    var days = Math.floor(hours / 24);

    if (days > 0) {
        return days === 1 ? '1 day ago' : days + ' days ago';
    } else if (hours > 0) {
        return hours === 1 ? '1 hour ago' : hours + ' hours ago';
    } else if (minutes > 0) {
        return minutes === 1 ? '1 minute ago' : minutes + ' minutes ago';
    } else {
        return 'Just now';
    }
}

// Utility function to show toast notifications
function showToast(message, type = 'info') {
    // Create toast HTML
    var toastHtml = `
        <div class="toast align-items-center text-white bg-${type} border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">
                    ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>
    `;
    
    // Add to toast container (create if doesn't exist)
    var toastContainer = document.getElementById('toast-container');
    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.id = 'toast-container';
        toastContainer.className = 'toast-container position-fixed bottom-0 end-0 p-3';
        document.body.appendChild(toastContainer);
    }
    
    // Add toast to container
    toastContainer.insertAdjacentHTML('beforeend', toastHtml);
    
    // Initialize and show the toast
    var toastElement = toastContainer.lastElementChild;
    var toast = new bootstrap.Toast(toastElement);
    toast.show();
    
    // Remove toast element after it's hidden
    toastElement.addEventListener('hidden.bs.toast', function() {
        toastElement.remove();
    });
}

// Function to validate forms
function validateForm(form) {
    var isValid = true;
    var requiredFields = form.querySelectorAll('[required]');
    
    requiredFields.forEach(function(field) {
        if (!field.value.trim()) {
            field.classList.add('is-invalid');
            isValid = false;
        } else {
            field.classList.remove('is-invalid');
        }
    });
    
    return isValid;
}

// Function to format phone numbers
function formatPhoneNumber(input) {
    var value = input.value.replace(/\D/g, '');
    var formattedValue = value.replace(/(\d{3})(\d{3})(\d{4})/, '($1) $2-$3');
    if (value.length === 10) {
        input.value = formattedValue;
    }
}

// Add phone number formatting to phone inputs
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('input[type="tel"]').forEach(function(input) {
        input.addEventListener('input', function() {
            formatPhoneNumber(this);
        });
    });
});

// Function to filter tables
function filterTable(input, tableId) {
    var filter = input.value.toLowerCase();
    var table = document.getElementById(tableId);
    var rows = table.getElementsByTagName('tr');
    
    for (var i = 1; i < rows.length; i++) { // Skip header row
        var row = rows[i];
        var cells = row.getElementsByTagName('td');
        var found = false;
        
        for (var j = 0; j < cells.length; j++) {
            if (cells[j].textContent.toLowerCase().indexOf(filter) > -1) {
                found = true;
                break;
            }
        }
        
        row.style.display = found ? '' : 'none';
    }
}

// Export functions for global use
window.SARA = {
    showToast: showToast,
    validateForm: validateForm,
    formatPhoneNumber: formatPhoneNumber,
    filterTable: filterTable,
    updateTimestamps: updateTimestamps
};