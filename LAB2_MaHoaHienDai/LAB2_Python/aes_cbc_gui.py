"""LAB 2: AES-256-CBC (Tkinter), Python 3.10+.
Setup: python -m pip install cryptography
Run: python aes_cbc_gui.py
Test: python aes_cbc_gui.py --self-test
This is an educational CBC demo; ciphertext is not authenticated.
"""
import base64
import secrets
import sys
import tkinter as tk
from tkinter import ttk, messagebox, filedialog
from cryptography.hazmat.primitives import padding
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes


def new_key():
    return base64.b64encode(secrets.token_bytes(32)).decode('ascii')


def new_iv():
    return base64.b64encode(secrets.token_bytes(16)).decode('ascii')


def _parse(key_b64, iv_b64):
    try:
        key = base64.b64decode(key_b64.strip(), validate=True)
        iv = base64.b64decode(iv_b64.strip(), validate=True)
    except Exception as ex:
        raise ValueError('Key và IV phải là Base64 hợp lệ.') from ex
    if len(key) != 32 or len(iv) != 16:
        raise ValueError('Key phải 32 byte và IV phải 16 byte.')
    return key, iv


def encrypt_text(plaintext, key_b64, iv_b64):
    key, iv = _parse(key_b64, iv_b64)
    padder = padding.PKCS7(128).padder()
    data = padder.update(plaintext.encode('utf-8')) + padder.finalize()
    enc = Cipher(algorithms.AES(key), modes.CBC(iv)).encryptor()
    return base64.b64encode(enc.update(data) + enc.finalize()).decode('ascii')


def decrypt_text(cipher_b64, key_b64, iv_b64):
    key, iv = _parse(key_b64, iv_b64)
    try:
        data = base64.b64decode(cipher_b64.strip(), validate=True)
        dec = Cipher(algorithms.AES(key), modes.CBC(iv)).decryptor()
        padded = dec.update(data) + dec.finalize()
        unpad = padding.PKCS7(128).unpadder()
        return (unpad.update(padded) + unpad.finalize()).decode('utf-8')
    except Exception as ex:
        raise ValueError('Giải mã thất bại: sai Key/IV hoặc dữ liệu bị sửa.') from ex


def self_test():
    long_text = 'Xin chào LAB 2 - mã hóa hiện đại! ' * 100
    for text in ['Xin chào AES-CBC', '', '1234567890123456', long_text]:
        key, iv = new_key(), new_iv()
        assert decrypt_text(encrypt_text(text, key, iv), key, iv) == text
    print('SELF-TEST OK')


class App(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title('AES-CBC Python - LAB 2')
        self.geometry('800x620')
        self.key = tk.StringVar(value=new_key())
        self.iv = tk.StringVar(value=new_iv())
        self.status = tk.StringVar(value='Sẵn sàng.')
        pane = ttk.Frame(self, padding=16)
        pane.pack(expand=True, fill='both')
        ttk.Label(pane, text='AES-256-CBC - Python / Tkinter', font=('Segoe UI', 17, 'bold')).pack(anchor='w')
        ttk.Label(pane, text='Plaintext:').pack(anchor='w')
        self.plain = tk.Text(pane, height=6, wrap='word')
        self.plain.pack(fill='both', expand=True)
        ttk.Label(pane, text='Key (Base64, 32 byte):').pack(anchor='w')
        ttk.Entry(pane, textvariable=self.key).pack(fill='x')
        ttk.Label(pane, text='IV (Base64, 16 byte):').pack(anchor='w')
        ttk.Entry(pane, textvariable=self.iv).pack(fill='x')
        ttk.Label(pane, text='Ciphertext (Base64):').pack(anchor='w')
        self.cipher = tk.Text(pane, height=6, wrap='word')
        self.cipher.pack(fill='both', expand=True)
        buttons = ttk.Frame(pane)
        buttons.pack(fill='x', pady=12)
        for label, fn in [('Generate Key', self.generate_key), ('Generate IV', self.generate_iv),
                          ('Encrypt', self.encrypt), ('Decrypt', self.decrypt),
                          ('Save File', self.save), ('Open File', self.open)]:
            ttk.Button(buttons, text=label, command=fn).pack(side='left', padx=3)
        ttk.Label(pane, textvariable=self.status).pack(anchor='w')

    def generate_key(self):
        self.key.set(new_key())
        self.iv.set(new_iv())

    def generate_iv(self):
        self.iv.set(new_iv())

    def encrypt(self):
        try:
            cipher = encrypt_text(self.plain.get('1.0', 'end-1c'), self.key.get(), self.iv.get())
            self.cipher.delete('1.0', 'end')
            self.cipher.insert('1.0', cipher)
            self.status.set('Đã mã hóa AES-CBC.')
        except Exception as ex:
            messagebox.showerror('Lỗi', str(ex))

    def decrypt(self):
        try:
            text = decrypt_text(self.cipher.get('1.0', 'end-1c'), self.key.get(), self.iv.get())
            self.plain.delete('1.0', 'end')
            self.plain.insert('1.0', text)
            self.status.set('Đã giải mã AES-CBC.')
        except Exception as ex:
            messagebox.showerror('Lỗi', str(ex))

    def save(self):
        path = filedialog.asksaveasfilename(defaultextension='.txt', filetypes=[('Text', '*.txt')])
        if path:
            with open(path, 'w', encoding='utf-8') as f:
                f.write('IV=' + self.iv.get() + '\n' + self.cipher.get('1.0', 'end-1c').strip() + '\n')
            self.status.set('Đã lưu tệp.')

    def open(self):
        path = filedialog.askopenfilename(filetypes=[('Text', '*.txt')])
        if path:
            with open(path, encoding='utf-8') as f:
                lines = f.read().strip().splitlines()
            if len(lines) < 2 or not lines[0].startswith('IV='):
                messagebox.showerror('Lỗi', 'Tệp phải có IV= ở dòng đầu và ciphertext ở dòng sau.')
                return
            self.iv.set(lines[0][3:].strip())
            self.cipher.delete('1.0', 'end')
            self.cipher.insert('1.0', lines[1].strip())
            self.status.set('Đã mở tệp, hãy nhập lại Key để giải mã.')


if __name__ == '__main__':
    if '--self-test' in sys.argv:
        self_test()
    else:
        App().mainloop()
