document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('.login-form');
  
    form.addEventListener('submit', async (e) => {
      e.preventDefault();
  
      const email = document.getElementById('usuario').value;
      const password = document.getElementById('password').value;
  
      const loginData = {
        email: email,
        password: password
      };
  
      try {
        const response = await fetch('/catalogo/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(loginData)
        });
  
        const tipo = await response.text();
  
        if (response.ok) {
          // ⬇️ Aquí guardamos el tipo de usuario en localStorage
          localStorage.setItem('tipoUsuario', tipo);
  
          // Redirigimos al catálogo
          window.location.href = '/frontend/pages/catalogo.html';
        } else {
          alert('Usuario no encontrado. Asegúrate de estar registrado.');
        }
      } catch (error) {
        console.error('Error al iniciar sesión:', error);
        alert('Ha ocurrido un error al iniciar sesión.');
      }
    });
  });
  
  
  
  