import { toast } from '@zerodevx/svelte-toast';

export const successToast = (m) =>
  toast.push(m, {
    theme: {
      '--toastBackground': 'rgba(25, 135, 84, 0.8)',
      '--toastColor': 'white',
      '--toastBarHeight': 0,
      '--toastBorder': ''
    }
  });

export const infoToast = (m) =>
  toast.push(m, {
    theme: {
      '--toastBackground': 'rgb(255, 255, 255, 255)',
      '--toastColor': 'black',
      '--toastBarHeight': 0
    }
  });

export const warningToast = (m) =>
  toast.push(m, {
    theme: {
      '--toastBackground': 'rgb(255, 193, 7, 0.8)',
      '--toastColor': 'black',
      '--toastBarHeight': 0
    }
  });

export const failureToast = (m) =>
  toast.push(m, {
    theme: {
      '--toastBackground': 'rgba(219, 26, 58, 0.8)',
      '--toastColor': 'white',
      '--toastBarHeight': 0,
      '--toastBorder': ''
    }
  });
