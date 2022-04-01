let logoutBtn = document.querySelector('#logout-btn');

logoutBtn.addEventListener('click', () => {
    localStorage.removeItem('jwt');
    localStorage.removeItem('user_id');

    window.location = '/index.html';
});

window.addEventListener('load', (event) => {
    populateReimbursementsTable();
});

let filterInput = document.querySelector('#filter-input');

filterInput.addEventListener('change', () => {
    populateReimbursementsTable();
});

async function populateReimbursementsTable() {
    const URL = 'http://34.125.243.234:8080/reimbursements';

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

        let filter = filterInput.options[filterInput.selectedIndex].value;

        for (let reimbursement of reimbursements) {
            if(filter != 'All' && filter != reimbursement.status) {
                continue;
            }

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

            if(!reimbursement.resolverUsername) {
                let resolveInput = document.createElement('select');
                
                let denyOption = document.createElement('option');
                denyOption.setAttribute('value', '2');
                denyOption.innerHTML = 'Deny';

                let approveOption = document.createElement('option');
                approveOption.setAttribute('value', '3');
                approveOption.innerText = 'Approve';

                resolveInput.appendChild(denyOption);
                resolveInput.appendChild(approveOption);

                let resolveButton = document.createElement('button');
                resolveButton.innerText = 'Submit';

                resolveButton.addEventListener('click', async () => {
                    let statusId = resolveInput.options[resolveInput.selectedIndex].value;

                    let resolveTime = new Date().toISOString().substring(0, 23).replace("T", " "); //JDBC timestamp escape format

                    try {
                        let res = await fetch(`http://34.125.243.234:8080/reimbursements/${reimbursement.id}?status=${statusId}&resolved=${resolveTime}`, {
                            method: 'PATCH',
                            headers: {
                                'Authorization': `Bearer ${localStorage.getItem('jwt')}`
                            }
                        });

                        if (res.status === 200) {
                            populateReimbursementsTable();
                        }

                    } catch (e) {
                        console.log(e);
                    }
                });

                tr.appendChild(resolveInput);
                tr.appendChild(resolveButton);
            }

            tbody.appendChild(tr);
        }
    }
    
}