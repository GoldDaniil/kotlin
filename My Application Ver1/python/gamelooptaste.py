import pygame
import sys
import math
import random

pygame.init()

WIDTH, HEIGHT = 600, 600
WHITE, BLACK, RED = (255, 255, 255), (0, 0, 0), (255, 0, 0)
FPS = 60
CIRCLE_RADIUS = 200

screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("попади в закрашенную зону")
clock = pygame.time.Clock()

font = pygame.font.SysFont(None, 75)
small_font = pygame.font.SysFont(None, 55)

angle = 0
base_speed = 360 / 5
speed = base_speed
arc_length = 60
score = 0
game_over = False
start_angle = random.randint(0, 360 - arc_length)

def draw_colored_zone(center, radius, start_angle, arc_len, color):
    start_angle_rad = math.radians(start_angle)
    arc_length_rad = math.radians(arc_len)
    end_angle_rad = start_angle_rad + arc_length_rad

    points = [center]
    for angle in range(int(start_angle_rad * 180 / math.pi), int(end_angle_rad * 180 / math.pi)):
        x = center[0] + int(radius * math.cos(math.radians(angle)))
        y = center[1] - int(radius * math.sin(math.radians(angle)))
        points.append((x, y))

    points.append(center)
    pygame.draw.polygon(screen, color, points)

def draw_moving_symbol(center, radius, angle, symbol):
    angle_rad = math.radians(angle)
    x = center[0] + int(radius * math.cos(angle_rad))
    y = center[1] - int(radius * math.sin(angle_rad))
    text = font.render(symbol, True, BLACK)
    text_rect = text.get_rect(center=(x, y))
    screen.blit(text, text_rect)

def draw_score(score):
    text = small_font.render(f"Результат: {score}", True, BLACK)
    screen.blit(text, (10, 10))

def check_click(center, radius, angle, s_angle, arc_len):
    global game_over, score, base_speed, speed, arc_length, start_angle
    angle = angle % 360
    s_angle = s_angle % 360
    end_angle = (s_angle + arc_len) % 360

    if s_angle < end_angle:
        if s_angle <= angle <= end_angle:
            score += 1
            arc_length = max(10, arc_length - 5)
            start_angle = random.randint(0, 360 - arc_length)
        else:
            game_over = True
    else:
        if angle >= s_angle or angle <= end_angle:
            score += 1
            arc_length = max(10, arc_length - 5)
            start_angle = random.randint(0, 360 - arc_length)
        else:
            game_over = True

    if score > 0 and score % 4 == 0:
        base_speed = max(360 / 2.5, base_speed - 360 / 5)
    speed = base_speed

while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()
        if event.type == pygame.MOUSEBUTTONDOWN and not game_over:
            check_click((WIDTH // 2, HEIGHT // 2), CIRCLE_RADIUS, angle, start_angle, arc_length)

    screen.fill(WHITE)

    if not game_over:
        draw_colored_zone((WIDTH // 2, HEIGHT // 2), CIRCLE_RADIUS, start_angle, arc_length, RED)
        draw_moving_symbol((WIDTH // 2, HEIGHT // 2), CIRCLE_RADIUS, angle, "C")
        angle = (angle + speed / FPS) % 360

    else:
        text = font.render("Game Over", True, BLACK)
        screen.blit(text, (WIDTH // 2 - text.get_width() // 2, HEIGHT // 2 - text.get_height() // 2))

    draw_score(score)

    pygame.display.flip()
    clock.tick(FPS)
