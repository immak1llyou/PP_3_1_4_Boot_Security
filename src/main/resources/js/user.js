document.addEventListener('DOMContentLoaded', async function () {
    await showUserEmailOnNavbar()
    await showUserName()
    await fillTableAboutUser();
});

async function dataAboutCurrentUser() {
    const response = await fetch("/api/user")
    return await response.json();
}

async function fillTableAboutUser() {
    const currentUserTable1 = document.getElementById('currentUserTable');
    const currentUser = await dataAboutCurrentUser();

    let currentUserTableHTML = "";
    currentUserTableHTML +=
        `<tr>
            <td>${currentUser.id}</td>
            <td>${currentUser.userName}</td>
            <td>${currentUser.name}</td>
            <td>${currentUser.surname}</td>
            <td>${currentUser.email}</td>
            <td>${currentUser.yearOfBirth}</td>
            <td>${currentUser.role.map(role => role.roleName).join(' ')}</td>
        </tr>`
    currentUserTable1.innerHTML = currentUserTableHTML;
}

async function showUserName() {
    const currentUserEmailNavbar = document.getElementById("currentUserEmailNavbar")
    const currentUser = await dataAboutCurrentUser();
    currentUserEmailNavbar.innerHTML =
        `<strong>${currentUser.email}</strong>
                 with roles:
                 ${currentUser.role.map(role => role.roleName).join(' ')}`;
}

async function showUserEmailOnNavbar() {
    const currentUserEmailNavbar = document.getElementById("currentUserName")
    const currentUser = await dataAboutCurrentUser();
    currentUserEmailNavbar.innerHTML =
        `<strong>${currentUser.name}</strong>`;
}