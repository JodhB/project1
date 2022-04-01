let logoutBtn = document.querySelector('#logout-btn');

logoutBtn.addEventListener('click', () => {
    localStorage.removeItem('jwt');
    localStorage.removeItem('user_id');

    window.location = '/index.html';
});

window.addEventListener('load', (event) => {
    populateReimbursementsTable();
});

let reimbursementSubmit = document.querySelector('#submit');
reimbursementSubmit.addEventListener('click', async () => {
    let amountInput = document.querySelector('#amount-input');
    let descriptionInput = document.querySelector('#description-input');
    let fileInput = document.querySelector('#file-input');
    let typeInput = document.querySelector('#type-input');

    let formData = new FormData();
    formData.append('amount', amountInput.value);
    formData.append('submitted', new Date().toISOString().substring(0, 23).replace("T", " ")); //JDBC timestamp escape format
    formData.append('description', descriptionInput.value);
    formData.append('image', fileInput.files[0]);
    formData.append('type', typeInput.options[typeInput.selectedIndex].value);

    try {
        let res = await fetch(`http://34.125.243.234:8080/users/${localStorage.getItem('user_id')}/reimbursements`, {
            method: 'POST',
            body: formData,
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwt')}`
            }
        });

        populateReimbursementsTable();
    } catch (e) {
        console.log(e);
    }
});

async function populateReimbursementsTable() {
    const URL = `http://34.125.243.234:8080/users/${localStorage.getItem('user_id')}/reimbursements`;

    let res = await fetch(URL, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    })

    if (res.status === 200) {
        let reimbursements = await res.json();

        let tbody = document.querySelector('#reimbursements-tbl > tbody');
        tbody.innerHTML = '';

        for (let reimbursement of reimbursements) {
            let tr = document.createElement('tr');

            let td1 = document.createElement('td');
            td1.innerText = reimbursement.id;

            let td2 = document.createElement('td');
            td2.innerText = reimbursement.amount;

            let td3 = document.createElement('td');
            td3.innerText = reimbursement.submitted;

            let td4 = document.createElement('td');
            td4.innerText = (reimbursement.resolverUsername ? reimbursement.resolved : 'Not resolved');
            td4.style.color = (reimbursement.resolverUsername ? td4.style.color : 'RGB(255, 0, 0)');

            let td5 = document.createElement('td');
            td5.innerText = reimbursement.description;

            let td6 = document.createElement('td');
            td6.innerText = reimbursement.authorUsername;

            let td7 = document.createElement('td');
            td7.innerText = (reimbursement.resolverUsername ? reimbursement.resolverUsername : 'Not resolved');
            td7.style.color = (reimbursement.resolverUsername ? td7.style.color : 'RGB(255, 0, 0)');

            let td8 = document.createElement('td');
            td8.innerText = reimbursement.status;

            let td9 = document.createElement('td');
            td9.innerText = reimbursement.type;

            let td10 = document.createElement('td');
            let imgElement = document.createElement('img');
            imgElement.setAttribute('src', reimbursement.receiptUrl);
            imgElement.style.height = '100px';
            td10.appendChild(imgElement);

            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            tr.appendChild(td6);
            tr.appendChild(td7);
            tr.appendChild(td8);
            tr.appendChild(td9);
            tr.appendChild(td10);

            tbody.appendChild(tr);
        }
    }
    
}

//Modal code
document.addEventListener('DOMContentLoaded', () => {
    // Functions to open and close a modal
    function openModal($el) {
      $el.classList.add('is-active');
    }
  
    function closeModal($el) {
      $el.classList.remove('is-active');
    }
  
    function closeAllModals() {
      (document.querySelectorAll('.modal') || []).forEach(($modal) => {
        closeModal($modal);
      });
    }
  
    // Add a click event on buttons to open a specific modal
    (document.querySelectorAll('.js-modal-trigger') || []).forEach(($trigger) => {
      const modal = $trigger.dataset.target;
      const $target = document.getElementById(modal);
      console.log($target);
  
      $trigger.addEventListener('click', () => {
        openModal($target);
      });
    });
  
    // Add a click event on various child elements to close the parent modal
    (document.querySelectorAll('.modal-background, .modal-close, .modal-card-head .delete, .modal-card-foot .button') || []).forEach(($close) => {
      const $target = $close.closest('.modal');
  
      $close.addEventListener('click', () => {
        closeModal($target);
      });
    });
  
    // Add a keyboard event to close all modals
    document.addEventListener('keydown', (event) => {
      const e = event || window.event;
  
      if (e.keyCode === 27) { // Escape key
        closeAllModals();
      }
    });
  });