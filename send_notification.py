#!/usr/bin/env python3
"""
Script de notificacao por e-mail da pipeline Jenkins.
Todas as configuracoes sao passadas via variaveis de ambiente — nenhum dado
sensivel ou endereco de e-mail hardcoded aqui.

Variaveis de ambiente obrigatorias:
  SMTP_HOST         - Ex.: smtp.gmail.com
  SMTP_PORT         - Ex.: 587
  SMTP_USER         - Endereco de e-mail remetente
  SMTP_PASS         - Senha ou App Password do remetente
  NOTIFICATION_EMAIL - Endereco(s) destinatario(s), separados por virgula
  BUILD_STATUS      - SUCCESS ou FAILURE
  JOB_NAME          - Nome do job Jenkins (injetado automaticamente)
  BUILD_NUMBER      - Numero do build Jenkins (injetado automaticamente)
  BUILD_URL         - URL do build Jenkins (injetado automaticamente)
"""
import smtplib
import sys
import os
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from datetime import datetime

SMTP_HOST          = os.environ.get("SMTP_HOST", "smtp.gmail.com")
SMTP_PORT          = int(os.environ.get("SMTP_PORT", "587"))
SMTP_USER          = os.environ.get("SMTP_USER", "")
SMTP_PASS          = os.environ.get("SMTP_PASS", "")
NOTIFICATION_EMAIL = os.environ.get("NOTIFICATION_EMAIL", "")
JOB_NAME           = os.environ.get("JOB_NAME", "PoorSofaScore")
BUILD_NUMBER       = os.environ.get("BUILD_NUMBER", "?")
BUILD_URL          = os.environ.get("BUILD_URL", "")
BUILD_STATUS       = os.environ.get("BUILD_STATUS", "UNKNOWN")
TIMESTAMP          = datetime.now().strftime("%d/%m/%Y %H:%M:%S")

if not NOTIFICATION_EMAIL:
    print("[ERRO] Variavel de ambiente NOTIFICATION_EMAIL nao configurada.")
    sys.exit(1)

if not SMTP_USER or not SMTP_PASS:
    print("[ERRO] SMTP_USER e/ou SMTP_PASS nao configurados.")
    sys.exit(1)

recipients = [r.strip() for r in NOTIFICATION_EMAIL.split(",") if r.strip()]

if BUILD_STATUS == "SUCCESS":
    symbol  = "[OK]"
    summary = f"A pipeline foi concluida com SUCESSO."
    detail  = f"Artefatos disponíveis em: {BUILD_URL}artifact/" if BUILD_URL else ""
else:
    symbol  = "[FALHA]"
    summary = "A pipeline FALHOU. Verifique os logs para mais detalhes."
    detail  = f"Console: {BUILD_URL}console" if BUILD_URL else ""

subject = f"{symbol} {BUILD_STATUS}: {JOB_NAME} #{BUILD_NUMBER}"

body = f"""Pipeline Jenkins — Notificacao Automatica
===========================================

Job        : {JOB_NAME}
Build      : #{BUILD_NUMBER}
Status     : {BUILD_STATUS}
Horario    : {TIMESTAMP}

{summary}
{detail}

Detalhes completos: {BUILD_URL}
---
Este e-mail foi gerado automaticamente pelo Jenkins.
Nao responda a esta mensagem.
"""

msg = MIMEMultipart()
msg["From"]    = SMTP_USER
msg["To"]      = ", ".join(recipients)
msg["Subject"] = subject
msg.attach(MIMEText(body, "plain", "utf-8"))

try:
    print(f"[INFO] Conectando em {SMTP_HOST}:{SMTP_PORT} ...")
    with smtplib.SMTP(SMTP_HOST, SMTP_PORT, timeout=30) as server:
        server.ehlo()
        server.starttls()
        server.ehlo()
        server.login(SMTP_USER, SMTP_PASS)
        server.sendmail(SMTP_USER, recipients, msg.as_string())
    print(f"[OK] E-mail de {BUILD_STATUS} enviado para: {', '.join(recipients)}")
except smtplib.SMTPAuthenticationError:
    print("[ERRO] Falha de autenticacao SMTP. Verifique SMTP_USER e SMTP_PASS.")
    sys.exit(1)
except Exception as exc:
    print(f"[AVISO] Nao foi possivel enviar o e-mail: {exc}")
    sys.exit(0)